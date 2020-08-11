package com.sammidev.demo.exception;

public class PenghinapanNotFoundException extends RuntimeException {
    public PenghinapanNotFoundException(String penghinapan_id_not_found) {
        super(penghinapan_id_not_found);
    }
}
