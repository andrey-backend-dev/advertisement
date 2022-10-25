package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.PurchaseDTO;
import com.senla.advertisement.dto.PurchaseRequestDTO;

import java.util.List;

public interface IPurchaseService {
    PurchaseDTO findById(int id);
    PurchaseDTO create(PurchaseRequestDTO purchaseRequestDTO, String token);
    List<PurchaseDTO> getList();
}
