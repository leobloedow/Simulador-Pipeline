import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;

public class Pipeline {
  private int[] R = new int[32];

  LinkedList<String> lista = new LinkedList<String>();
  Scanner scanner = new Scanner(System.in);

  public LinkedList<String> readFile() {
    try {
      BufferedReader br = new BufferedReader(
          new InputStreamReader(new FileInputStream("inst.txt")));
      try {
        String line;
        while ((line = br.readLine()) != null) {
          lista.add(line);
        }
      } finally {
        br.close();
      }
    } catch (Exception e) {
      System.out.println("Arquivo não encontrado " + e.getMessage());
    }
    return lista;
  }

  public LinkedList<String[]> listaPronta(LinkedList<String> lista) {
    LinkedList<String[]> newList = new LinkedList<String[]>();
    for (int i = 0; i < lista.size(); i++) {
      String[] inst = lista.get(i).split(" ");
      newList.add(inst);
    }
    return newList;
  }

  public String[] identificaInstrucao(String[] instrucao) {
    if (instrucao != null) {

      System.out.println("A instrução " + Arrays.toString(instrucao) + " foi buscada");
    }
    if (instrucao[0].equals("add")) {
      return instrucao;
    } else if (instrucao[0].equals("addi")) {
      return instrucao;
    } else if (instrucao[0].equals("sub")) {
      return instrucao;
    } else if (instrucao[0].equals("subi")) {
      return instrucao;
    } else if (instrucao[0].equals("beq")) {
      return instrucao;
    } else if (instrucao[0].equals("j")) {
      return instrucao;
    } else if (instrucao[0].equals("halt")) {
      return instrucao;
    }
    return null;
  }

  public LinkedList valorRegistradores(String[] input) {

    // retorna LinkedList {instrucao[], copiaDosRegistradores[]}

    if (input != null) {

      LinkedList output = new LinkedList();
      output.add(input);

      //Aqui é feita uma cópia temporária dos registradores para armazenar o resultado da operação, que só será realmente armazenado no registrador de destino na última etapa.
      output.add(R.clone());

      System.out.println("A instrução " + Arrays.toString((String[]) output.get(0)) + " foi decodificada.");
      

      return output;

    }
    return null;
  }

  public LinkedList calcula(LinkedList input) {

    // retorna LinkedList {instrucao[], copiaDosRegistradores[],
    // resultadoDaOperacao}}


    if (input != null) {
      
    int numeroImediato = (Integer.parseInt(((String[]) input.get(0))[3]));

      System.out.println("A instrução " + Arrays.toString((String[]) input.get(0)) + " foi executada.");

      LinkedList output = input;
      // add
      if (((String[]) input.get(0))[0].equals("add")) {
        int conteudoRegistrador1 = ((int[]) input.get(1))[Integer.parseInt(((String[]) input.get(0))[2])];
        int conteudoRegistrador2 = ((int[]) input.get(1))[Integer.parseInt(((String[]) input.get(0))[3])];
        System.out.println("INSTRUCAO ADD");
        int result = conteudoRegistrador1
            + conteudoRegistrador2;
        output.add(result);
        return output;
      }
      // addi
      if (((String[]) input.get(0))[0].equals("addi")) {
        int conteudoRegistrador1 = ((int[]) input.get(1))[Integer.parseInt(((String[]) input.get(0))[2])];
        System.out.println("INSTRUCAO ADDI");
        int result = conteudoRegistrador1
            + numeroImediato;
        output.add(result);
        return output;
      }
      // sub
      if (((String[]) input.get(0))[0].equals("sub")) {
        int conteudoRegistrador1 = ((int[]) input.get(1))[Integer.parseInt(((String[]) input.get(0))[2])];
        int conteudoRegistrador2 = ((int[]) input.get(1))[Integer.parseInt(((String[]) input.get(0))[3])];
        int result = conteudoRegistrador1
            - conteudoRegistrador2;
        output.add(result);
        return output;
      }
      // subi
      if (((String[]) input.get(0))[0].equals("subi")) {
        int conteudoRegistrador1 = ((int[]) input.get(1))[Integer.parseInt(((String[]) input.get(0))[2])];
        int result = conteudoRegistrador1
            - numeroImediato;
        output.add(result);
        return output;
      }

    }
    return null;
  }

  public void atualizaRegs(LinkedList input) {
    // atualiza os registradores
    if (input != null) {

      R[Integer.parseInt(((String[]) input.get(0))[1])] = (Integer) input.get(2);

      System.out.println("A instrução " + Arrays.toString((String[]) input.get(0)) + " escreveu nos registradores.");

      for (int i = 0; i < R.length; i++) {
        System.out.println(R[i]);
      }

    }
  }

  public void executa(LinkedList<String[]> lista) {

    String[] input2 = null;
    LinkedList input3 = null;
    LinkedList input4 = null;
    int i = 0;
    int clock = 0;

    while (true) {

      if (i > lista.size() && input2 == null && input3 == null && input4 == null) {
        System.out.println("Fim da execução das instruções");
        break;
      }

      System.out.println("clock " + clock + "\nPressione ENTER para o próximo clock");
      scanner.nextLine();

      atualizaRegs(input4);
      input4 = calcula(input3);
      input3 = valorRegistradores(input2);

      // halt
      if (input2 != null && input2[0].equals("halt")) {
        break;
      }

      // j
      if (input2 != null && input2[0].equals("j")) {
        i += Integer.parseInt(input2[1]) - 1;
      }

      // beq
      if (input2 != null && input2[0].equals("beq")) {
        if (R[Integer.parseInt(((String) input2[1]))] == R[Integer.parseInt(((String) input2[2]))]) {
          i += Integer.parseInt(input2[3]) - 1;
        }
      }

      if (i < lista.size()) {
        input2 = identificaInstrucao(lista.get(i));
      } else {
        input2 = null;
      }

      clock++;

      i++;

    }
  }

}
