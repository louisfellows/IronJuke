package com.louisfellows.ironjuke.view.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Utility class for making alterations to images.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class ImageHandler {

    /**
     * Resizes the given image to be used in the currently playing song bar
     * 
     * @param originalImage
     *            the original image
     * @param type
     *            the buffered image type.
     * @param newHeight
     *            the height of the new image
     * @param newWidth
     *            the width of the new image
     * @return the resized image
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int newHeight, int newWidth) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }
}
