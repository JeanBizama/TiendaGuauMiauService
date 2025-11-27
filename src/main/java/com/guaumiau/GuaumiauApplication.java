package com.guaumiau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@SpringBootApplication
public class GuaumiauApplication {

    public static void main(String[] args) {
        try {
            setupOracleWallet();
        } catch (IOException e) {
            System.err.println("Error crítico al configurar la Wallet: " + e.getMessage());
            e.printStackTrace();
        }

        SpringApplication.run(GuaumiauApplication.class, args);
    }

    // Método estático para preparar la Wallet
    private static void setupOracleWallet() throws IOException {
        Path tempWalletDir = Paths.get(System.getProperty("java.io.tmpdir"), "Wallet_TiendaGuauMiau");

        if (Files.exists(tempWalletDir)) {
            FileSystemUtils.deleteRecursively(tempWalletDir);
        }
        Files.createDirectories(tempWalletDir);

        String[] walletFiles = {"cwallet.sso", "ewallet.p12", "keystore.jks", "ojdbc.properties", "sqlnet.ora", "tnsnames.ora", "truststore.jks"};

        System.out.println("Copiando Wallet a: " + tempWalletDir.toString());

        for (String fileName : walletFiles) {
            try {

                ClassPathResource resource = new ClassPathResource("Wallet_TiendaGuauMiau/" + fileName);

                if (resource.exists()) {
                    Files.copy(resource.getInputStream(), tempWalletDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    System.out.println("Aviso: No se encontró " + fileName + " (puede ser opcional).");
                }
            } catch (Exception e) {
                System.out.println("No se pudo copiar " + fileName + ": " + e.getMessage());
            }
        }

        System.setProperty("oracle.net.tns_admin", tempWalletDir.toString());
        System.out.println("Propiedad oracle.net.tns_admin establecida.");
    }

}
