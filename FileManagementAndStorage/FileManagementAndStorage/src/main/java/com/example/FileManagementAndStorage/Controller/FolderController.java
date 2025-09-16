package com.example.FileManagementAndStorage.Controller;

import com.example.FileManagementAndStorage.ModelDTO.FolderDTO;
import com.example.FileManagementAndStorage.Service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @PostMapping("/create")
    public ResponseEntity<FolderDTO> createFolder(@RequestParam String name,
                                                  @RequestParam(required = false) Long parentId) {
        return ResponseEntity.ok(folderService.createFolder(name, parentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderDTO> getFolder(@PathVariable Long id) {
        return ResponseEntity.ok(folderService.getFolder(id));
    }

    @PatchMapping("/{id}/rename")
    public ResponseEntity<FolderDTO> renameFolder(@PathVariable Long id,
                                                  @RequestParam String newName) {
        return ResponseEntity.ok(folderService.renameFolder(id, newName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFolder(@PathVariable Long id) {
        folderService.deleteFolder(id);
        return ResponseEntity.ok("Folder deleted successfully!");
    }
}
