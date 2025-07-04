package onecenter.com.br.ecommerce.pessoa.service.imagem;

import onecenter.com.br.ecommerce.utils.Constantes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private static final String diretorioUpload = "./uploads/";

    public String salvarImagem(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException(Constantes.ArquivoImagemVazio);
        }

        Path diretorioPath = Paths.get(diretorioUpload);
        if (!Files.exists(diretorioPath)) {
            Files.createDirectories(diretorioPath);
        }

        String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path caminhoArquivo = diretorioPath.resolve(nomeArquivo);

        Files.copy(file.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + nomeArquivo;
    }
}
