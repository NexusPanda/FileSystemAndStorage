package com.example.FileManagementAndStorage.Service;

import com.example.FileManagementAndStorage.Model.FileModel;
import com.example.FileManagementAndStorage.Model.FileShare;
import com.example.FileManagementAndStorage.Model.UserEntity;
import com.example.FileManagementAndStorage.ModelDTO.FileShareDTO;
import com.example.FileManagementAndStorage.Repository.FileRepository;
import com.example.FileManagementAndStorage.Repository.FileShareRepository;
import com.example.FileManagementAndStorage.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileShareRepository fileShareRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void shareFile(Long fileId, Long userId) {
        FileModel file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FileShare share = new FileShare();
        share.setFile(file);
        share.setSharedWith(user);

        fileShareRepository.save(share);
    }

    @Override
    public List<FileShareDTO> getSharedUsers(Long fileId) {
        List<FileShare> shares = fileShareRepository.findByFileId(fileId);

        return shares.stream()
                .map(share -> {
                    FileShareDTO dto = new FileShareDTO();
                    dto.setId(share.getId());
                    dto.setFileId(share.getFile().getId());
                    dto.setSharedWithUserId(share.getSharedWith().getId());
                    // if you have permission field:
                    if (share.getPermission() != null) {
                        dto.setPermission(share.getPermission().name());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void revokeShare(Long fileId, Long userId) {
        Optional<FileShare> opt = fileShareRepository.findByFileIdAndSharedWithId(fileId, userId);
        FileShare share = opt.orElseThrow(() -> new RuntimeException("Share record not found"));
        fileShareRepository.delete(share);
    }

}
