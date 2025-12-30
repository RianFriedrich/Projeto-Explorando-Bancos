import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

interface DatabaseStrategy {
    String getDbName();
    String getUseCase();
    String getExplanation();
    String[] getColumnNames();
    
    void create(String[] data);
    List<String[]> read();
    void update(int index, String[] data);
    void delete(int index);
}

class MongoImplementation implements DatabaseStrategy {
    private List<String[]> dataStore = new ArrayList<>();

    public MongoImplementation() {
        dataStore.add(new String[]{"1", "Arroz 5kg", "R$ 25.00"});
        dataStore.add(new String[]{"2", "Feijão", "R$ 8.50"});
    }

    @Override
    public String getDbName() { return "MongoDB"; }
    @Override
    public String getUseCase() { return "Gestão de Produtos (Mercado)"; }
    @Override
    public String getExplanation() {
        return "Por que MongoDB?\n\n" +
               "O MongoDB é um banco NoSQL orientado a documentos (JSON).\n" +
               "Para um mercado, produtos podem ter atributos variados (ex: uma TV tem voltagem, " +
               "uma fruta tem validade). O esquema flexível do Mongo é ideal para catálogos complexos " +
               "onde os dados não seguem uma tabela rígida.";
    }
    @Override
    public String[] getColumnNames() { return new String[]{"ID", "Produto", "Preço"}; }

    @Override
    public void create(String[] data) { dataStore.add(data); }
    @Override
    public List<String[]> read() { return dataStore; }
    @Override
    public void update(int index, String[] data) { dataStore.set(index, data); }
    @Override
    public void delete(int index) { dataStore.remove(index); }
}

class FirebaseImplementation implements DatabaseStrategy {
    private List<String[]> dataStore = new ArrayList<>();

    public FirebaseImplementation() {
        dataStore.add(new String[]{"FUNC-01", "Ana Silva", "TI"});
        dataStore.add(new String[]{"FUNC-02", "Carlos Souza", "RH"});
    }

    @Override
    public String getDbName() { return "Firebase (Firestore)"; }
    @Override
    public String getUseCase() { return "Sistema de Funcionários (Tempo Real)"; }
    @Override
    public String getExplanation() {
        return "Por que Firebase?\n\n" +
               "O Firebase brilha em sincronização em tempo real.\n" +
               "Para um sistema de funcionários onde múltiplos gerentes podem editar escalas " +
               "ou dados simultaneamente, o Firebase atualiza a tela de todos instantaneamente " +
               "sem precisar recarregar (via WebSockets/Listeners).";
    }
    @Override
    public String[] getColumnNames() { return new String[]{"ID", "Nome", "Departamento"}; }

    @Override
    public void create(String[] data) { dataStore.add(data); }
    @Override
    public List<String[]> read() { return dataStore; }
    @Override
    public void update(int index, String[] data) { dataStore.set(index, data); }
    @Override
    public void delete(int index) { dataStore.remove(index); }
}

class PostgresImplementation implements DatabaseStrategy {
    private List<String[]> dataStore = new ArrayList<>();

    public PostgresImplementation() {
        dataStore.add(new String[]{"1001", "Fatura Energia", "Pendente"});
        dataStore.add(new String[]{"1002", "Pagamento Fornecedor", "Pago"});
    }

    @Override
    public String getDbName() { return "PostgreSQL"; }
    @Override
    public String getUseCase() { return "Sistema Financeiro/Contábil"; }
    @Override
    public String getExplanation() {
        return "Por que PostgreSQL?\n\n" +
               "O Postgres é um banco Relacional (SQL) robusto e ACID-compliant.\n" +
               "Para sistemas financeiros, a integridade dos dados e transações seguras são cruciais. " +
               "Relacionamentos rígidos garantem que não existam pagamentos órfãos sem fatura.";
    }
    @Override
    public String[] getColumnNames() { return new String[]{"ID Transação", "Descrição", "Status"}; }

    @Override
    public void create(String[] data) { dataStore.add(data); }
    @Override
    public List<String[]> read() { return dataStore; }
    @Override
    public void update(int index, String[] data) { dataStore.set(index, data); }
    @Override
    public void delete(int index) { dataStore.remove(index); }
}

class CouchbaseImplementation implements DatabaseStrategy {
    private List<String[]> dataStore = new ArrayList<>();

    public CouchbaseImplementation() {
        dataStore.add(new String[]{"Sessao_998", "User_X", "Ativa"});
        dataStore.add(new String[]{"Sessao_999", "User_Y", "Expirada"});
    }

    @Override
    public String getDbName() { return "Couchbase"; }
    @Override
    public String getUseCase() { return "Cache de Sessão / Alta Performance"; }
    @Override
    public String getExplanation() {
        return "Por que Couchbase?\n\n" +
               "O Couchbase é um banco Key-Value e Document Store focado em performance extrema.\n" +
               "Ideal para armazenar sessões de usuário, carrinhos de compra temporários ou cache, " +
               "onde a velocidade de leitura/escrita na memória é mais importante que relacionamentos complexos.";
    }
    @Override
    public String[] getColumnNames() { return new String[]{"Key (Chave)", "Usuário", "Estado"}; }

    @Override
    public void create(String[] data) { dataStore.add(data); }
    @Override
    public List<String[]> read() { return dataStore; }
    @Override
    public void update(int index, String[] data) { dataStore.set(index, data); }
    @Override
    public void delete(int index) { dataStore.remove(index); }
}

public class GerenciadorBancoDados extends JFrame {

    private JComboBox<String> cmbDatabases;
    private JLabel lblUseCase;
    private JTable table;
    private DefaultTableModel tableModel;
    private DatabaseStrategy currentStrategy;
    
    private Map<String, DatabaseStrategy> strategies;

    public GerenciadorBancoDados() {
        setTitle("Gerenciador Multi-Database System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        strategies = new HashMap<>();
        strategies.put("PostgreSQL", new PostgresImplementation());
        strategies.put("MongoDB", new MongoImplementation());
        strategies.put("Firebase", new FirebaseImplementation());
        strategies.put("Couchbase", new CouchbaseImplementation());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        JLabel lblSelect = new JLabel("Selecione o Sistema de Banco de Dados:");
        cmbDatabases = new JComboBox<>(new String[]{"PostgreSQL", "MongoDB", "Firebase", "Couchbase"});
        
        JButton btnInfo = new JButton("ℹ Informação");
        btnInfo.setBackground(new Color(70, 130, 180));
        btnInfo.setForeground(Color.BLACK);

        topPanel.add(lblSelect);
        topPanel.add(cmbDatabases);
        topPanel.add(btnInfo);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblUseCase = new JLabel("Contexto: Selecione um banco...");
        lblUseCase.setFont(new Font("Arial", Font.BOLD, 14));
        lblUseCase.setForeground(new Color(0, 100, 0));
        
        centerPanel.add(lblUseCase, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnAdd = new JButton("Adicionar (Create)");
        JButton btnEdit = new JButton("Editar (Update)");
        JButton btnDelete = new JButton("Excluir (Delete)");
        
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnEdit);
        bottomPanel.add(btnDelete);
        
        add(bottomPanel, BorderLayout.SOUTH);

        cmbDatabases.addActionListener(e -> updateInterface());

        btnInfo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                currentStrategy.getExplanation(), 
                "Sobre " + currentStrategy.getDbName(), 
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnAdd.addActionListener(e -> {
            String[] cols = currentStrategy.getColumnNames();
            JPanel panel = new JPanel(new GridLayout(0, 2));
            JTextField[] fields = new JTextField[cols.length];
            
            for (int i = 0; i < cols.length; i++) {
                panel.add(new JLabel(cols[i] + ":"));
                fields[i] = new JTextField();
                panel.add(fields[i]);
            }
            
            int result = JOptionPane.showConfirmDialog(null, panel, "Novo Registro", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String[] newData = new String[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    newData[i] = fields[i].getText();
                }
                currentStrategy.create(newData);
                refreshTable();
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para editar.");
                return;
            }

            String[] cols = currentStrategy.getColumnNames();
            JPanel panel = new JPanel(new GridLayout(0, 2));
            JTextField[] fields = new JTextField[cols.length];
            
            for (int i = 0; i < cols.length; i++) {
                panel.add(new JLabel(cols[i] + ":"));
                fields[i] = new JTextField((String) tableModel.getValueAt(selectedRow, i));
                panel.add(fields[i]);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Registro", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String[] newData = new String[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    newData[i] = fields[i].getText();
                }
                currentStrategy.update(selectedRow, newData);
                refreshTable();
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                currentStrategy.delete(selectedRow);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
            }
        });

        cmbDatabases.setSelectedIndex(0);
    }

    private void updateInterface() {
        String selected = (String) cmbDatabases.getSelectedItem();
        currentStrategy = strategies.get(selected);
        
        lblUseCase.setText("Exemplo de Uso: " + currentStrategy.getUseCase());
        
        tableModel.setColumnIdentifiers(currentStrategy.getColumnNames());
        
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<String[]> data = currentStrategy.read();
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        
        SwingUtilities.invokeLater(() -> {
            new GerenciadorBancoDados().setVisible(true);
        });
    }
}