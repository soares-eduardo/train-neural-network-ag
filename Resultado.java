public class Resultado {
    private int[][] tabuleiro;
    private boolean isEmpate;

    public Resultado(int[][] tabuleiro, boolean isEmpate) {
        this.tabuleiro = tabuleiro;
        this.isEmpate = isEmpate;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public boolean getIsEmpate() {
        return isEmpate;
    }
}