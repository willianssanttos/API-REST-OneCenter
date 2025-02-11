package onecenter.com.br.ecommerce.service.imagem;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private static final String diretorioUpload = "src/main/resources/static/uploads/";

    public String salvarImagem(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Arquivo de imagem vazio!");
        }

        // Criar diretório caso não exista
        Files.createDirectories(Paths.get(diretorioUpload));

        // Gerar um nome único para a imagem
        String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path caminhoArquivo = Paths.get(diretorioUpload + nomeArquivo);

        Files.createDirectories(caminhoArquivo.getParent());
        Files.write(caminhoArquivo, file.getBytes());

        // Salvar o arquivo
        Files.copy(file.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + nomeArquivo;
    }
}
