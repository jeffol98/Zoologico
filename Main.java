import java.sql.ResultSet;
import java.util.Scanner;

import Data.DbContext;

public class Main
{
	
	public static void main(String[] args) {

		inicio();

	}

	// MÉTODO PRINCIPAL
	public static void inicio() {
		System.out.println("\n Selecione uma opção: \n");
		
		System.out.println("1 - Cadastrar Animal");
		System.out.println("2 - Listar Animal");
		System.out.println("3 - Atualizar Animal");
		System.out.println("4 - Deletar Animal");

		System.out.print("\n OPÇÃO: ");

		Scanner scanner = new Scanner(System.in);

		if (scanner.hasNextInt()) {
			int opcaoSelecionada = scanner.nextInt();

			if (opcaoSelecionada >= 1 && opcaoSelecionada <= 4) {
				executaOpcao(opcaoSelecionada);			
			}
			else {
				mensagemStatus("Opção Inválida.");
			}
	
			inicio();
		}
		else {
			mensagemStatus("Opção Inválida.");
		}

		scanner.nextLine();
		
		inicio();
	}

	// MÉTODO QUE VAI CHAMAR A EXECUÇÃO DAS OPERAÇÃO DE ACORDO COM A OPÇÃO SELECIONADA
	public static void executaOpcao(int opcaoSelecionada) {
		switch (opcaoSelecionada) {
			case 1:
				cadastrarAnimal();
				break;
			case 2:
				listarAnimais();
				break;
			case 3:
				atualizarAnimal();
				break;
			case 4:
				deletarAnimal();
				break;
			default:
				break;
		}
	}

	public static void cadastrarAnimal() {
		
		System.out.println("\n Informe o nome do animal: ");

		Scanner scanner = new Scanner(System.in);

		if (scanner.hasNext()) {
			
			String nomeAnimal = scanner.next();

			DbContext database = new DbContext();

			try {
				database.conectarBanco();

				boolean statusQuery = database.executarUpdateSql("INSERT INTO public.animais(nome) VALUES ('" + nomeAnimal + "')");

				if (statusQuery) {
					mensagemStatus("Novo animal cadastrado com sucesso !");
				}

				database.desconectarBanco();

			} catch (Exception e) {}
	
			inicio();
		}

	}

	public static void listarAnimais() {

		DbContext database = new DbContext();

		try {
			database.conectarBanco();

			ResultSet resultadoConsulta = database.executarQuerySql("SELECT * FROM public.animais");

			while (resultadoConsulta.next()) {
				System.out.println("ID - " + resultadoConsulta.getString("id") + " | NOME - " + resultadoConsulta.getString("nome"));
            }

			database.desconectarBanco();

		} catch (Exception e) {}
	
		inicio();
	}

	public static void deletarAnimal() {
		
		System.out.println("\n Informe o ID do animal a ser deletado: ");

		Scanner scanner = new Scanner(System.in);

		if (scanner.hasNextInt()) {
			
			int idAnimal = scanner.nextInt();

			DbContext database = new DbContext();

			try {
				database.conectarBanco();

				boolean statusQuery = database.executarUpdateSql("DELETE FROM public.animais WHERE id = " + idAnimal);

				if (statusQuery) {
					mensagemStatus("Animal deletado com sucesso !");
				}

				database.desconectarBanco();

			} catch (Exception e) {}
	
			inicio();
		}

	}

	public static void atualizarAnimal() {
		
		System.out.print("\n Informe o ID do animal a ser atualizado: ");

		Scanner scanner = new Scanner(System.in);

		if (scanner.hasNextInt()) {
			
			int idAnimal = scanner.nextInt();
			String novoNomeAnimal = "";

			System.out.print("\n Informe o novo nome do animal a ser atualizado: ");

			if (scanner.hasNext()) {
			
				novoNomeAnimal = scanner.next();

				DbContext database = new DbContext();

				try {
					database.conectarBanco();

					boolean statusQuery = database.executarUpdateSql("UPDATE public.animais SET nome = '" + novoNomeAnimal + "' WHERE id = " + idAnimal);

					if (statusQuery) {
						mensagemStatus("Animal atualizado com sucesso !");
					}

					database.desconectarBanco();

				} catch (Exception e) {}
			}
	
			inicio();
		}

	}
	

	// MÉTODO RESPONSÁVEL SOMENTE POR EXIBIR UMA MENSAGEM NA TELA
	public static void mensagemStatus(String mensagem) {
		System.out.print("\n");
		System.out.print("---------------------");
		System.out.print("\n " + mensagem + " \n");
		System.out.print("---------------------");
		System.out.print("\n");
	}


}


