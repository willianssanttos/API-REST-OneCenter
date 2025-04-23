package onecenter.com.br.ecommerce.pessoa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onecenter.com.br.ecommerce.pessoa.enums.RolesEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesEntity {

    private RolesEnum nomeRole;

}
