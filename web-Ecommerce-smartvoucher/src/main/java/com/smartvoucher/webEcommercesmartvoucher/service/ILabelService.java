package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.LabelDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import java.util.List;

public interface ILabelService {
    List<LabelDTO> getAllLabel();
    ResponseOutput getAllLabel(int page, int limit, String sortBy, String sortField);
    List<String> getAllNameByLabel();
}
