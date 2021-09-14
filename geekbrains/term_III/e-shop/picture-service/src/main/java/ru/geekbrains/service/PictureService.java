package ru.geekbrains.service;

import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    String createPicture(byte[] picture);
}
