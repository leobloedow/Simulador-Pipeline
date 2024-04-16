import java.util.LinkedList;

public class Main {
  public static void main(String[] args) {
    Pipeline p = new Pipeline();
    LinkedList<String[]> instrucoes = p.listaPronta(p.readFile());

    for (int i = 0; i < instrucoes.size(); i++) {
      System.out.println(instrucoes.get(i)[0]);
    }

    p.executa(instrucoes);
  }
}