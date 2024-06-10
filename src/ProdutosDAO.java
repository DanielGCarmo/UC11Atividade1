
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.execute();
            prep.close();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutosDAO Cadastrar: " + erro.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        String sql = "SELECT * FROM produtos";
        conn = new conectaDAO().connectDB();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutosDAO Listar: " + erro.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }

        return listagem;
    }

    //Adiciona um método para atualizar o status do produto para "Vendido".
    public void venderProduto(int id) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);

            prep.executeUpdate();
            prep.close();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutosDAO Vender: " + erro.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    //Adiciona um método para listar todos os produtos com o status "Vendido".
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> listagemVendidos = new ArrayList<>();

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagemVendidos.add(produto);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutosDAO Listar Vendidos: " + erro.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }

        return listagemVendidos;
    }

}
