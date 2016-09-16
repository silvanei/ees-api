package org.ees.api.agenda.infra.service;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.service.ImageFileService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.imageio.ImageIO;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by silvanei on 15/09/16.
 */
public class ImageFileServiceImpl implements ImageFileService {

    @Override
    public String upload(String fileName, InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
        String formatName = fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf(".") + 1).toLowerCase();

        if(!formatName.equals("jpg") && !formatName.equals("png")) {
            throw new WebApplicationException("Permitido somente imagens JPG ou PNG");
        }

        File imgPng = new File(UPLOAD_FILE_LOCATION + fileName + ".png");
        imgPng.delete();

        fileName = UPLOAD_FILE_LOCATION + fileName + ".png";

        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(uploadedInputStream);

            if (null == originalImage) {
                throw new WebApplicationException("Arquivo não aceito");
            }

            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImage = resizeImage(originalImage, type);
            ImageIO.write(resizeImage, formatName, new File(fileName));

            return fileName;

        } catch (IOException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    @Override
    public BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    @Override
    public String base64Encode(Integer salaoId) {
        try {
            byte[] bytes;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            InputStream inputStream = new FileInputStream(getPath(salaoId));//You can get an inputStream using any IO API
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            bytes = output.toByteArray();
            String encodedString = Base64.encode(bytes, Base64.BASE64DEFAULTLENGTH);

            return encodedString;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getPath(Integer salaoId) {
        return UPLOAD_FILE_LOCATION + Integer.toString(salaoId) +".png";
    }

    @Override
    public boolean delete(Integer salaoId) {
        File img = new File(getPath(salaoId));
        if(img.delete()) {
            return true;
        }

        return false;
    }
}
