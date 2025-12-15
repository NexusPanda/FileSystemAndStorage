package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.ModelDTO.FileShareDTO;

import java.util.List;

public interface ShareService {

    void shareFile(Long fileId, Long userId);

    List<FileShareDTO> getSharedUsers(Long fileId);

    void revokeShare(Long fileId, Long userId);

    String getDownloadUrl(Long fileId, Long userId);


}
