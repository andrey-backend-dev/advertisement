package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.AdvertisementRequestDTO;
import com.senla.advertisement.dto.AdvertisementResponseDTO;
import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.ProductDTO;
import com.senla.advertisement.dto.UserDTO;
import com.senla.advertisement.utils.enums.SortMethod;
import com.senla.advertisement.utils.enums.Status;

import java.util.List;

public interface IAdvertisementService {
    AdvertisementResponseDTO findById(int id);
    AdvertisementResponseDTO create(AdvertisementRequestDTO advertisementDTO, String token);
    boolean deleteById(int id, String token);
    List<AdvertisementResponseDTO> getList(SortMethod sorting, String orderName, boolean wholeList);
    List<AdvertisementResponseDTO> getListBySearch(String search);
    int count();
    boolean updateById(int id, AdvertisementRequestDTO changedAdvertisement, String token);
    UserDTO getUserById(int id);
    List<ProductDTO> getProductsById(int id);
    List<CommentDTO> getCommentsById(int id);
    AdvertisementResponseDTO promoteAdvertisementById(int id, int weeks, String token);
    AdvertisementResponseDTO changeStatusById(int id, Status status);
}
