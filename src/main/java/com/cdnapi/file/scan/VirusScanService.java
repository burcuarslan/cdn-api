package com.cdnapi.file.scan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

@Service
@Slf4j
public class VirusScanService {

    private static final String CLAMAV_HOST = "172.21.207.38";
    private static final int CLAMAV_PORT = 3310;

    private final ClamavClient clamavClient;

    public VirusScanService() {
        try {
            this.clamavClient = new ClamavClient(CLAMAV_HOST, CLAMAV_PORT);
            log.info("ClamAV client successfully initialized");
        } catch (Exception e) {
            log.error("Failed to initialize ClamAV client", e);
            throw new RuntimeException("Could not initialize ClamAV client", e);
        }
    }

    /**
     * InputStream ile dosya taraması
     * @param input Dosya içeriği InputStream olarak
     * @return true eğer temizse, false eğer virüs bulunmuşsa
     * @throws IOException
     */
    public boolean isClean(InputStream input) throws IOException {
        try {
            ScanResult result = clamavClient.scan(input);
            if (result instanceof ScanResult.OK) {
                log.info("File scan result: CLEAN");
                return true;
            } else if (result instanceof ScanResult.VirusFound) {
                Map<String, Collection<String>> viruses = ((ScanResult.VirusFound) result).getFoundViruses();

                log.warn("File scan result: INFECTED - " + viruses.keySet());
                return false;
            }
        } catch (Exception e) {
            log.error("Error during file scan", e);
            throw new IOException("Error during file scan", e);
        }
        return false;
    }
}
