package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 Fluxo 3: Acessar listagem, criar novo lançamento, validar lançamento criado, editar
 lançamento, validar edição, remover lançamento e validar remoção;
 */
public class Fluxo3 extends Fluxo2 {
    @Test
    public void excluirLancamentoTest() {
        this.excluirLancamento();
    }
}
