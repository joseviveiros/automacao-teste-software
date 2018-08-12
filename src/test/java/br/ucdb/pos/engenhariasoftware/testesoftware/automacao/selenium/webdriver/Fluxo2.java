package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import org.testng.annotations.Test;

/**
 Fluxo 2: Acessar listagem, criar novo lançamento, validar lançamento criado, editar
 lançamento e validar edição;
 */
public class Fluxo2 extends Fluxo1 {
    @Test()
    public void editarLancamentoTest() {
        this.editarLancamento();
    }
}
