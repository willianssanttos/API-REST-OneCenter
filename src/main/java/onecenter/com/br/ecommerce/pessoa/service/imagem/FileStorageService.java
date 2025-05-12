package onecenter.com.br.ecommerce.pessoa.service.imagem;

import onecenter.com.br.ecommerce.utils.Constantes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private static final String diretorioUpload = "src/main/resources/static/uploads/";

    public String salvarImagem(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException(Constantes.ArquivoImagemVazio);
        }

        // Caminho do diretório
        Path diretorioPath = Paths.get(diretorioUpload);

        // Criar diretório caso não exista
        if (!Files.exists(diretorioPath)) {
            Files.createDirectories(diretorioPath);
        }

        // Caminho do arquivo no diretório
        Path caminhoArquivoExistente = diretorioPath.resolve(file.getOriginalFilename());

        // Se a imagem já existir, retorna o mesmo caminho
        if (Files.exists(caminhoArquivoExistente)) {
            return "/uploads/" + file.getOriginalFilename();
        }

        // Gerar um nome único para a imagem caso não exista
        String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path caminhoArquivoNovo = diretorioPath.resolve(nomeArquivo);

        // Salvar o arquivo
        Files.copy(file.getInputStream(), caminhoArquivoNovo, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + nomeArquivo;
    }
}