package org.maxim.RestApi.DTO.Converter;

import org.maxim.RestApi.DTO.FileDTO;
import org.maxim.RestApi.model.UFile;

public class FileConverter {
    public static FileDTO toDTO(UFile uFile) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(uFile.getId());
        fileDTO.setName(uFile.getName());
        fileDTO.setFilePath(uFile.getFilePath());
        return fileDTO;
    }

    public static UFile toEntity(FileDTO fileDTO) {
        UFile uFile = new UFile();
        uFile.setId(fileDTO.getId());
        uFile.setName(fileDTO.getName());
        uFile.setFilePath(fileDTO.getFilePath());
        return uFile;
    }
}

