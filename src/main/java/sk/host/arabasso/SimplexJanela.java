package sk.host.arabasso;

import sk.host.arabasso.printer.SimplexHtmlPrinter;
import sk.host.arabasso.printer.SimplexPrinter;
import sk.host.arabasso.printer.SimplexTextPrinter;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Created by arabasso on 29/10/2016.
 */
public class SimplexJanela extends JFrame {
    private JTextField foTextField;
    private JTextArea restricoesTextArea;
    private JButton resolverButton;
    private JTextArea variaveisBasicastextArea;
    private JTextArea variaveisNaoBasicastextArea;
    private JTextArea valorZTextArea;
    private JPanel simplexPanel;
    private JEditorPane passoEditorPane;
    private final Document passoDocument;
    private SimplexPrinter printer;

    public SimplexJanela(String title) throws HeadlessException {
        super(title);

        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        getContentPane().add(simplexPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        resolverButton.addActionListener(this::resolverButtonClick);

        HTMLEditorKit kit = new HTMLEditorKit();

        passoEditorPane.setEditorKit(kit);

        StyleSheet styleSheet = kit.getStyleSheet();

        styleSheet.addRule("table {  width: 100%; }");
        styleSheet.addRule("table tr th { border: 1px solid #000; background: #999999; }");
        styleSheet.addRule("table tr td, table tr th { border: 1px solid #000000; }");

        passoDocument = kit.createDefaultDocument();

        passoEditorPane.setDocument(passoDocument);

        printer = new SimplexHtmlPrinter();
    }

    private void resolverButtonClick(ActionEvent e){
        try {
            String fo = foTextField.getText();
            String restricoes = restricoesTextArea.getText();

            SimplexConstrutor construtor = new SimplexConstrutor();

            Simplex simplex = construtor.construir(fo, Arrays.asList(restricoes.split("\n")));

            SimplexSolucao solucao = simplex.solucao();

            StringBuilder sb = new StringBuilder();

            sb.append(printer.tudo(simplex, solucao));

            while (!solucao.otima) {
                simplex = simplex.proximoAlgoritmo();

                solucao = simplex.solucao();

                sb.append(printer.tudo(simplex, solucao));
            }

            passoEditorPane.setText(sb.toString());

            SimplexTextPrinter textPrinter = new SimplexTextPrinter();

            variaveisBasicastextArea.setText(textPrinter.variaveisBasicas(simplex, solucao));
            variaveisNaoBasicastextArea.setText(textPrinter.variaveisNaoBasicas(simplex, solucao));
            valorZTextArea.setText(textPrinter.valorZ(simplex, solucao));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Exceção", JOptionPane.ERROR_MESSAGE);
        }
    }
}
