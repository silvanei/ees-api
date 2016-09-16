package org.ees.api.agenda.service;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Created by silvanei on 15/09/16.
 */
public interface ImageFileService {

    public static final int IMG_WIDTH = 128;
    public static final int IMG_HEIGHT = 128;
    public static final String UPLOAD_FILE_LOCATION = "/tmp/agenda/";
    public static final String IMG_DEFAULT = "/9j/4AAQSkZJRgABAQEASABIAAD/4QCARXhpZgAASUkqAAgAAAAEABoBBQABAAAAPgAAABsBBQABAAAARgAAACgBAwABAAAAAgAAAGmHBAABAAAATgAAAAAAAABIAAAAAQAAAEgAAAABAAAAAwAAkAcABAAAADAyMTAAoAcABAAAADAxMDABoAMAAQAAAP//AAAAAAAA/+EDBmh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8APD94cGFja2V0IGJlZ2luPSfvu78nIGlkPSdXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQnPz4KPHg6eG1wbWV0YSB4bWxuczp4PSdhZG9iZTpuczptZXRhLyc+CjxyZGY6UkRGIHhtbG5zOnJkZj0naHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyc+CgogPHJkZjpEZXNjcmlwdGlvbiB4bWxuczp4bXA9J2h0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8nPgogIDx4bXA6Q3JlYXRvclRvb2w+QWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzPC94bXA6Q3JlYXRvclRvb2w+CiA8L3JkZjpEZXNjcmlwdGlvbj4KCiA8cmRmOkRlc2NyaXB0aW9uIHhtbG5zOnhtcE1NPSdodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vJz4KICA8eG1wTU06SW5zdGFuY2VJRD54bXAuaWlkOjhERDQ3MkYwM0Q4QTExRTNBODk2OUREMDM0OTM5NkJDPC94bXBNTTpJbnN0YW5jZUlEPgogIDx4bXBNTTpJbnN0YW5jZUlEPnhtcC5paWQ6OERENDcyRjAzRDhBMTFFM0E4OTY5REQwMzQ5Mzk2QkM8L3htcE1NOkluc3RhbmNlSUQ+CiAgPHhtcE1NOkRvY3VtZW50SUQgcmRmOnJlc291cmNlPSd4bXAuZGlkOjhERDQ3MkYxM0Q4QTExRTNBODk2OUREMDM0OTM5NkJDJyAvPgogIDx4bXBNTTpEZXJpdmVkRnJvbSByZGY6cGFyc2VUeXBlPSdSZXNvdXJjZSc+CiAgPC94bXBNTTpEZXJpdmVkRnJvbT4KIDwvcmRmOkRlc2NyaXB0aW9uPgoKPC9yZGY6UkRGPgo8L3g6eG1w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";

    public String upload(String fileName, InputStream uploadedInputStream, FormDataContentDisposition fileDetail);

    public BufferedImage resizeImage(BufferedImage originalImage, int type);

    public String base64Encode(Integer salaoId);

    public String getPath(Integer salaoId);

    public boolean delete(Integer salaoId);
}
