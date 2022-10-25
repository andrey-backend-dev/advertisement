package com.senla.advertisement.services;

import com.senla.advertisement.dao.AdvertisementDAO;
import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.dto.AdvertisementRequestDTO;
import com.senla.advertisement.dto.AdvertisementResponseDTO;
import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.ProductDTO;
import com.senla.advertisement.dto.UserDTO;
import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.AdvertisementRating;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.IAdvertisementRateService;
import com.senla.advertisement.services.interfaces.IAdvertisementRatingService;
import com.senla.advertisement.services.interfaces.IAdvertisementService;
import com.senla.advertisement.services.interfaces.ICommentService;
import com.senla.advertisement.services.interfaces.IProductService;
import com.senla.advertisement.utils.enums.SortMethod;
import com.senla.advertisement.utils.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdvertisementService implements IAdvertisementService {

    private AdvertisementDAO advertisementDAO;
    private UserDAO userDAO;
    private IAdvertisementRateService advertisementRateService;
    private IAdvertisementRatingService advertisementRatingService;
    private ICommentService commentService;
    private IProductService productService;
    @Value("${business.costOfOneWeek}")
    private int costOfOneWeek;
    private JwtProvider jwtProvider;

    @Autowired
    public AdvertisementService(AdvertisementDAO advertisementDAO, UserDAO userDAO, IAdvertisementRateService advertisementRateService,
                                IAdvertisementRatingService advertisementRatingService, ICommentService commentService,
                                IProductService productService, JwtProvider jwtProvider) {
        this.advertisementDAO = advertisementDAO;
        this.userDAO = userDAO;
        this.advertisementRatingService = advertisementRatingService;
        this.advertisementRateService = advertisementRateService;
        this.commentService = commentService;
        this.productService = productService;
        this.jwtProvider = jwtProvider;
    }


    @Override
    @Transactional(readOnly = true)
    public AdvertisementResponseDTO findById(int id) {
        return new AdvertisementResponseDTO(advertisementDAO.findById(id).orElse(null));
    }


    @Override
    @Transactional
    public AdvertisementResponseDTO create(AdvertisementRequestDTO advertisementDTO, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Advertisement advertisement = new Advertisement();
        advertisement.setName(advertisementDTO.getName());
        advertisement.setAbout(advertisementDTO.getAbout());
        advertisement.setUser(user);
        advertisement.setCreated(LocalDateTime.now());
        advertisement.setStatus(Status.ALIVE);
        advertisement = advertisementDAO.save(advertisement);

        advertisement.setRating(advertisementRatingService.create(advertisement));

        return new AdvertisementResponseDTO(advertisement);
    }


    @Override
    @Transactional
    public boolean deleteById(int id, String token) {
        User requestedUser = userDAO.findByUsername(jwtProvider.getUsername(token));

        if (advertisementDAO.findById(id).orElse(null).getUser() == requestedUser) {
            advertisementDAO.deleteAd(id);
            return true;
        }

        return false;
    }


    @Override
    @Transactional
    public List<AdvertisementResponseDTO> getList(SortMethod sorting, String orderName, boolean wholeList) {
        Sort sort;
        advertisementRatingService.correctAdvertisementPromotions();

        if (sorting == SortMethod.NULL && orderName.equals("null")) {
            orderName = "desc";
        } else if (sorting != SortMethod.NULL && orderName.equals("null")) {
            orderName = "asc";
        }

        if (orderName.equalsIgnoreCase("asc")) {
            sort = sorting == SortMethod.NULL ? null : Sort.by(Sort.Order.asc(sorting.getName()));

            if (sort == null) {

                if (wholeList) {
                    return advertisementDAO.findWholeListByRatingSortAsc(orderName).stream().map(AdvertisementResponseDTO::new).toList();
                }

                return advertisementDAO.findByRatingSortAsc(orderName).stream().map(AdvertisementResponseDTO::new).toList();
            }
        } else {
            sort = sorting == SortMethod.NULL ? null : Sort.by(Sort.Order.desc(sorting.getName()));

            if (sort == null) {

                if (wholeList) {
                    return advertisementDAO.findWholeListByRatingSortDesc(orderName).stream().map(AdvertisementResponseDTO::new).toList();
                }

                return advertisementDAO.findByRatingSortDesc(orderName).stream().map(AdvertisementResponseDTO::new).toList();
            }
        }

        if (wholeList) {
            return advertisementDAO.findAll(sort).stream().map(AdvertisementResponseDTO::new).toList();
        }

        return advertisementDAO.findByStatus(Status.ALIVE, sort).stream().map(AdvertisementResponseDTO::new).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementResponseDTO> getListBySearch(String search) {
        return advertisementDAO.findByNameLike("%" + search + "%").stream().map(AdvertisementResponseDTO::new).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public int count() {
        return (int) advertisementDAO.count();
    }


    @Override
    @Transactional
    public boolean updateById(int id, AdvertisementRequestDTO changedAdvertisement, String token) {
        Advertisement advertisement = advertisementDAO.findById(id).orElse(null);

        if (advertisementDAO.findById(id).orElse(null).getUser() == userDAO.findByUsername(jwtProvider.getUsername(token))) {
            if (changedAdvertisement.getName() != null && !changedAdvertisement.getName().equals(advertisement.getName()))
                advertisement.setName(changedAdvertisement.getName());
            if (changedAdvertisement.getAbout() != null && !changedAdvertisement.getAbout().equals(advertisement.getAbout()))
                advertisement.setAbout(changedAdvertisement.getAbout());
            return true;
        }

        return false;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(int id) {
        return new UserDTO(advertisementDAO.findById(id).orElse(null).getUser());
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsById(int id) {
        return advertisementDAO.findById(id).orElse(null).getProducts().stream().map(ProductDTO::new).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsById(int id) {
        return advertisementDAO.findById(id).orElse(null).getComments().stream().map(CommentDTO::new).toList();
    }

    @Override
    @Transactional
    public AdvertisementResponseDTO promoteAdvertisementById(int id, int weeks, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Advertisement advertisement = advertisementDAO.findById(id).orElse(null);

        if (advertisement.getUser() == user &&
                (advertisement.getRating().getPaymentPeriod() == null || advertisement.getRating().getPaymentPeriod().isBefore(LocalDateTime.now()))
                && user.getMoney() > costOfOneWeek * weeks) {
            AdvertisementRating rating = advertisement.getRating();
            rating.setPromotedValue((short) (rating.getValue() + 500));
            rating.setPaymentPeriod(LocalDateTime.now().plusWeeks(weeks));
            user.setMoney(user.getMoney() - costOfOneWeek * weeks);
            return new AdvertisementResponseDTO(advertisement);
        }

        return null;
    }

    @Override
    @Transactional
    public AdvertisementResponseDTO changeStatusById(int id, Status status) {
        Advertisement advertisement = advertisementDAO.findById(id).orElse(null);
        advertisement.setStatus(status);
        return new AdvertisementResponseDTO(advertisement);
    }
}
