package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 Fluxo 5: Acessar listagem, validar a exibição de mensagens de campos obrigatório
 para todos os campos, voltar para tela de listagem com botão Cancelar e clicar no
 botão recarregar.
 */
public class Fluxo5 extends LancamentoTest {
    @Test
    public void validarCamposObrigatoriosTest() {
        this.validarCamposObrigatorios();
        lancamentoPage.cancela();
        listaLancamentosPage.recarregar();
    }
}
