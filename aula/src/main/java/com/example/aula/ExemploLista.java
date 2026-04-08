package com.example.aula;

import com.example.aula.dao.EnderecoDAO;
import com.example.aula.dao.PessoaDAO;
import com.example.aula.model.Endereco;
import com.example.aula.model.Pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExemploLista {

    private static EnderecoDAO enderecoDAO;
    private static PessoaDAO pessoaDAO;
    private static Scanner scanner;

    public static void main(String[] args) {
        enderecoDAO = new EnderecoDAO();
        pessoaDAO = new PessoaDAO();
        scanner = new Scanner(System.in);

        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> gerenciarEnderecos();
                case "2" -> gerenciarPessoas();
                case "3" -> {
                    System.out.println("\nSUCESSO: Programa finalizado!");
                    rodando = false;
                }
                default -> System.out.println("\nERRO: Opção inválida!");
            }
        }

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    SISTEMA DE GERENCIAMENTO - MENU PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Gerenciar Endereços");
        System.out.println("2. Gerenciar Pessoas");
        System.out.println("3. Sair");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void gerenciarEnderecos() {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("      GERENCIAR ENDEREÇOS");
            System.out.println("-".repeat(50));
            System.out.println("1. Inserir novo endereço");
            System.out.println("2. Editar endereço");
            System.out.println("3. Excluir endereço");
            System.out.println("4. Buscar endereço por ID");
            System.out.println("5. Buscar endereço por logradouro");
            System.out.println("6. Listar todos os endereços");
            System.out.println("7. Listar endereços por estado");
            System.out.println("8. Listar endereços por cidade");
            System.out.println("9. Voltar ao menu anterior");
            System.out.print("\nEscolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> inserirEndereco();
                case "2" -> editarEndereco();
                case "3" -> excluirEndereco();
                case "4" -> buscarEnderecoPorId();
                case "5" -> buscarEnderecoPorLogradouro();
                case "6" -> listarTodosEnderecos();
                case "7" -> listarEnderecosPorEstado();
                case "8" -> listarEnderecosPorCidade();
                case "9" -> voltarMenu = true;
                default -> System.out.println("ERRO: Opção inválida!");
            }
        }
    }

    private static void inserirEndereco() {
        System.out.println("\n--- INSERIR NOVO ENDEREÇO ---");
        
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        
        System.out.print("Estado (2 letras): ");
        String estado = scanner.nextLine();
        
        System.out.print("País: ");
        String pais = scanner.nextLine();

        Endereco endereco = new Endereco(0, logradouro, numero, cidade, estado, pais, null);

        if (enderecoDAO.inserir(endereco)) {
            System.out.println("SUCESSO: Endereço inserido com sucesso!");
        } else {
            System.out.println("ERRO: Erro ao inserir endereço!");
        }
    }

    private static void editarEndereco() {
        System.out.println("\n--- EDITAR ENDEREÇO ---");
        
        System.out.print("ID do endereço a editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Endereco endereco = enderecoDAO.recuperarPorId(id);
        if (endereco == null) {
            System.out.println("ERRO: Endereço não encontrado!");
            return;
        }

        System.out.println("\nDados atuais: " + endereco.getEnderecoCompleto());
        
        System.out.print("\nNovo logradouro (ou Enter para manter): ");
        String logradouro = scanner.nextLine();
        if (!logradouro.isBlank()) endereco.setLogradouro(logradouro);
        
        System.out.print("Novo número (ou Enter para manter): ");
        String numero = scanner.nextLine();
        if (!numero.isBlank()) endereco.setNumero(numero);
        
        System.out.print("Nova cidade (ou Enter para manter): ");
        String cidade = scanner.nextLine();
        if (!cidade.isBlank()) endereco.setCidade(cidade);

        if (enderecoDAO.editar(endereco)) {
            System.out.println("SUCESSO: Endereço atualizado com sucesso!");
        } else {
            System.out.println("ERRO: Erro ao atualizar endereço!");
        }
    }

    private static void excluirEndereco() {
        System.out.println("\n--- EXCLUIR ENDEREÇO ---");
        System.out.print("ID do endereço a excluir: ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Pessoa> pessoasComEndereco = pessoaDAO.recuperarPorEnderecoId(id);
        if (!pessoasComEndereco.isEmpty()) {
            System.out.println("\n⚠️  Este endereço possui " + pessoasComEndereco.size() + " pessoa(s):");
            for (Pessoa p : pessoasComEndereco) {
                System.out.println("   - " + p.getNomeCompleto());
            }
            System.out.println("   Após exclusão, o endereco_id delas será NULL");
        }

        System.out.print("\nConfirmar exclusão? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            if (enderecoDAO.excluir(id)) {
                System.out.println("SUCESSO: Endereço excluído com sucesso!");
                if (!pessoasComEndereco.isEmpty()) {
                    System.out.println("SUCESSO: Pessoas vinculadas agora possuem endereco_id = NULL");
                }
            } else {
                System.out.println("ERRO: Erro ao excluir endereço!");
            }
        }
    }

    private static void buscarEnderecoPorId() {
        System.out.print("\nID do endereço: ");
        int id = Integer.parseInt(scanner.nextLine());

        Endereco endereco = enderecoDAO.recuperarPorId(id);
        if (endereco != null) {
            System.out.println("\nSUCESSO: Endereço encontrado:");
            System.out.println("   " + endereco.getEnderecoCompleto());
        } else {
            System.out.println("ERRO: Endereço não encontrado!");
        }
    }

    private static void buscarEnderecoPorLogradouro() {
        System.out.print("\nLogradouro (ou parte dele): ");
        String logradouro = scanner.nextLine();

        List<Endereco> enderecos = enderecoDAO.recuperarPorLogradouro(logradouro);
        if (enderecos.isEmpty()) {
            System.out.println("ERRO: Nenhum endereço encontrado!");
        } else {
            System.out.println("\nSUCESSO: " + enderecos.size() + " endereço(s) encontrado(s):");
            for (Endereco e : enderecos) {
                System.out.println("   [" + e.getId() + "] " + e.getEnderecoCompleto());
            }
        }
    }

    private static void listarTodosEnderecos() {
        List<Endereco> enderecos = enderecoDAO.recuperarTodos();
        
        if (enderecos.isEmpty()) {
            System.out.println("ERRO: Nenhum endereço cadastrado!");
        } else {
            System.out.println("\nSUCESSO: Total de " + enderecos.size() + " endereço(s):");
            for (Endereco e : enderecos) {
                System.out.println("   [" + e.getId() + "] " + e.getEnderecoCompleto());
            }
        }
    }

    private static void listarEnderecosPorEstado() {
        System.out.print("\nEstado (sigla, ex: SP): ");
        String estado = scanner.nextLine();

        List<Endereco> enderecos = enderecoDAO.recuperarPorEstado(estado);
        if (enderecos.isEmpty()) {
            System.out.println("ERRO: Nenhum endereço encontrado em " + estado);
        } else {
            System.out.println("\nSUCESSO: " + enderecos.size() + " endereço(s) em " + estado + ":");
            for (Endereco e : enderecos) {
                System.out.println("   [" + e.getId() + "] " + e.getEnderecoCompleto());
            }
        }
    }

    private static void listarEnderecosPorCidade() {
        System.out.print("\nCidade: ");
        String cidade = scanner.nextLine();

        List<Endereco> enderecos = enderecoDAO.recuperarPorCidade(cidade);
        if (enderecos.isEmpty()) {
            System.out.println("ERRO: Nenhum endereço encontrado em " + cidade);
        } else {
            System.out.println("\nSUCESSO: " + enderecos.size() + " endereço(s) em " + cidade + ":");
            for (Endereco e : enderecos) {
                System.out.println("   [" + e.getId() + "] " + e.getEnderecoCompleto());
            }
        }
    }

    private static void gerenciarPessoas() {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("      GERENCIAR PESSOAS");
            System.out.println("-".repeat(50));
            System.out.println("1. Inserir nova pessoa");
            System.out.println("2. Editar pessoa");
            System.out.println("3. Excluir pessoa");
            System.out.println("4. Buscar pessoa por ID");
            System.out.println("5. Buscar pessoa por nome");
            System.out.println("6. Buscar pessoa pela idade");
            System.out.println("7. Listar todas as pessoas");
            System.out.println("8. Listar pessoas sem endereço (orfãs)");
            System.out.println("9. Listar pessoas por endereço");
            System.out.println("10. Voltar ao menu anterior");
            System.out.print("\nEscolha uma opção: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> inserirPessoa();
                case "2" -> editarPessoa();
                case "3" -> excluirPessoa();
                case "4" -> buscarPessoaPorId();
                case "5" -> buscarPessoaPorNome();
                case "6" -> buscarPessoaPorIdade();
                case "7" -> listarTodasPessoas();
                case "8" -> listarPessoasSemEndereco();
                case "9" -> listarPessoasPorEndereco();
                case "10" -> voltarMenu = true;
                default -> System.out.println("ERRO: Opção inválida!");
            }
        }
    }

    private static void inserirPessoa() {
        System.out.println("\n--- INSERIR NOVA PESSOA ---");
        System.out.println("⚠️  REGRA: Uma pessoa NÃO pode ser inserida sem um endereço válido!");
        
        List<Endereco> enderecos = enderecoDAO.recuperarTodos();
        if (enderecos.isEmpty()) {
            System.out.println("ERRO: Nenhum endereço cadastrado! Insira um endereço primeiro.");
            return;
        }

        System.out.println("\nEndereços disponíveis:");
        for (Endereco e : enderecos) {
            System.out.println("   [" + e.getId() + "] " + e.getEnderecoCompleto());
        }

        System.out.print("\nID do endereço: ");
        int enderecoId = Integer.parseInt(scanner.nextLine());

        if (enderecoDAO.recuperarPorId(enderecoId) == null) {
            System.out.println("ERRO: Endereço inválido!");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();
        
        System.out.print("Data de nascimento (YYYY-MM-DD): ");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
        
        System.out.print("CPF (apenas números): ");
        String cpf = scanner.nextLine();

        Pessoa pessoa = new Pessoa(0, nome, sobrenome, dataNascimento, cpf, enderecoId, null);

        if (pessoaDAO.inserir(pessoa)) {
            System.out.println("SUCESSO: Pessoa inserida com sucesso!");
            System.out.println("ℹ️ Idade calculada: " + pessoa.calcularIdade() + " anos");
        } else {
            System.out.println("ERRO: Erro ao inserir pessoa!");
        }
    }

    private static void editarPessoa() {
        System.out.println("\n--- EDITAR PESSOA ---");
        
        System.out.print("ID da pessoa a editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Pessoa pessoa = pessoaDAO.recuperarPorId(id);
        if (pessoa == null) {
            System.out.println("ERRO: Pessoa não encontrada!");
            return;
        }

        System.out.println("\nDados atuais: " + pessoa.getNomeCompleto() + 
                          " | Idade: " + pessoa.calcularIdade());
        System.out.println("⚠️  REGRA: Uma pessoa NÃO pode ser editada sem um endereço válido!");

        List<Endereco> enderecos = enderecoDAO.recuperarTodos();
        System.out.println("\nEndereços disponíveis:");
        for (Endereco e : enderecos) {
            System.out.println("   [" + e.getId() + "] " + e.getEnderecoCompleto());
        }

        System.out.print("\nNovo ID do endereço (ou Enter para manter " + pessoa.getEnderecoId() + "): ");
        String enderecoInput = scanner.nextLine();
        if (!enderecoInput.isBlank()) {
            pessoa.setEnderecoId(Integer.parseInt(enderecoInput));
        }

        System.out.print("Novo nome (ou Enter para manter): ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) pessoa.setNome(nome);

        if (pessoaDAO.editar(pessoa)) {
            System.out.println("SUCESSO: Pessoa atualizada com sucesso!");
        } else {
            System.out.println("ERRO: Erro ao atualizar pessoa!");
        }
    }

    private static void excluirPessoa() {
        System.out.print("\nID da pessoa a excluir: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Confirmar exclusão? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            if (pessoaDAO.excluir(id)) {
                System.out.println("SUCESSO: Pessoa excluída com sucesso!");
            } else {
                System.out.println("ERRO: Erro ao excluir pessoa!");
            }
        }
    }

    private static void buscarPessoaPorId() {
        System.out.print("\nID da pessoa: ");
        int id = Integer.parseInt(scanner.nextLine());

        Pessoa pessoa = pessoaDAO.recuperarPorId(id);
        if (pessoa != null) {
            System.out.println("\nSUCESSO: Pessoa encontrada:");
            exibirPessoa(pessoa);
        } else {
            System.out.println("ERRO: Pessoa não encontrada!");
        }
    }

    private static void buscarPessoaPorNome() {
        System.out.print("\nNome (ou parte dele): ");
        String nome = scanner.nextLine();

        List<Pessoa> pessoas = pessoaDAO.recuperarPorNome(nome);
        if (pessoas.isEmpty()) {
            System.out.println("ERRO: Nenhuma pessoa encontrada!");
        } else {
            System.out.println("\nSUCESSO: " + pessoas.size() + " pessoa(s) encontrada(s):");
            for (Pessoa p : pessoas) {
                exibirPessoa(p);
            }
        }
    }

    private static void buscarPessoaPorIdade() {
        System.out.print("\nIdade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        List<Pessoa> pessoas = pessoaDAO.recuperarPorIdade(idade);
        if (pessoas.isEmpty()) {
            System.out.println("ERRO: Nenhuma pessoa com " + idade + " ano(s)!");
        } else {
            System.out.println("\nSUCESSO: " + pessoas.size() + " pessoa(s) com " + idade + " ano(s):");
            for (Pessoa p : pessoas) {
                exibirPessoa(p);
            }
        }
    }

    private static void listarTodasPessoas() {
        List<Pessoa> pessoas = pessoaDAO.recuperarTodas();
        
        if (pessoas.isEmpty()) {
            System.out.println("ERRO: Nenhuma pessoa cadastrada!");
        } else {
            System.out.println("\nSUCESSO: Total de " + pessoas.size() + " pessoa(s):");
            for (Pessoa p : pessoas) {
                exibirPessoa(p);
            }
        }
    }

    private static void listarPessoasSemEndereco() {
        List<Pessoa> pessoas = pessoaDAO.recuperarSemEndereco();
        
        if (pessoas.isEmpty()) {
            System.out.println("\nSUCESSO: Nenhuma pessoa órfã!");
        } else {
            System.out.println("\n⚠️  " + pessoas.size() + " pessoa(s) SEM ENDEREÇO:");
            for (Pessoa p : pessoas) {
                exibirPessoa(p);
            }
        }
    }

    private static void listarPessoasPorEndereco() {
        System.out.print("\nID do endereço: ");
        int enderecoId = Integer.parseInt(scanner.nextLine());

        List<Pessoa> pessoas = pessoaDAO.recuperarPorEnderecoId(enderecoId);
        if (pessoas.isEmpty()) {
            System.out.println("ERRO: Nenhuma pessoa neste endereço!");
        } else {
            System.out.println("\nSUCESSO: " + pessoas.size() + " pessoa(s) neste endereço:");
            for (Pessoa p : pessoas) {
                exibirPessoa(p);
            }
        }
    }

    private static void exibirPessoa(Pessoa p) {
        String enderecoInfo = p.getEnderecoId() != null ? 
            "[" + p.getEnderecoId() + "]" : "[SEM ENDEREÇO]";
        
        System.out.println("   [ID: " + p.getId() + "] " + p.getNomeCompleto() + 
                          " | CPF: " + p.getCpfFormatado() + " | Idade: " + 
                          p.calcularIdade() + " | End.: " + enderecoInfo);
    }
}
