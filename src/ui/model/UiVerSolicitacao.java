/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.model;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.AlunoDisciplina;
import model.Alunos;
import model.Disciplinas;
import model.Funcionarios;
import mycustom.CustomFrame;
import mycustom.ItemSelection;
import mycustom.Table;
import ui.MainFrame;

/**
 *
 * @author thomaz
 */
public class UiVerSolicitacao extends CustomFrame {

    /**
     * Creates new form UiVerSolicitacao
     */
    public UiVerSolicitacao() {
        initComponents();
        
        List<AlunoDisciplina> ads = loadTable();
        tSolicitacoes.setModel(new Table<>(ads, colums));
        tSolicitacoes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                
                
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) { 
                    AlunoDisciplina ad = ads.get(row);
                    Alunos aluno = ad.getAluno();
                    
                    List<Disciplinas> disciplinasesNew = aluno.getDisciplinasList1();
                    List<Disciplinas> disciplinases = aluno.getDisciplinasList();
                    
                    int r = yesNoCancel("Deseja aceitar a inscrição desse aluno?");
                    if( r != JOptionPane.CANCEL_OPTION ) {
                        disciplinases.remove(ad.getDisciplina());
                    } else {
                        return;
                    }
                    
                    switch( r ) {
                        case JOptionPane.YES_OPTION:
                            disciplinasesNew.add(ad.getDisciplina());
                            aluno.setDisciplinasList1(disciplinasesNew);
                            
                        case JOptionPane.NO_OPTION:
                            aluno.setDisciplinasList(disciplinases);
                            try {
                                MainFrame.ALUNO_CONTROLLER.edit(aluno);
                            } catch (Exception ex) {
                                Logger.getLogger(UiRelCursoDisciplina.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                    
                    tSolicitacoes.setModel(new Table<>(loadTable(), colums));
                }
            }
        });
    }

    private int yesNoCancel(String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, 
                theMessage,"Aceita/Recusar", JOptionPane.YES_NO_CANCEL_OPTION);
        return result;
    }
    
    private List<AlunoDisciplina> loadTable() {
        List<AlunoDisciplina> ads = new ArrayList<>();
        List<Alunos> alunos = MainFrame.ALUNO_CONTROLLER.findAlunosEntities();
        for (Alunos aluno : alunos) {
            for (Disciplinas disciplinasList : aluno.getDisciplinasList()) {
                ads.add(new AlunoDisciplina(aluno, disciplinasList));
            }
        }
        return ads;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        tSolicitacoes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tSolicitacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tSolicitacoes.setEnabled(false);
        jScrollPane1.setViewportView(tSolicitacoes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private static final Object[] colums = {"Aluno", "Disciplina", "Curso"};

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JTable tSolicitacoes;
    // End of variables declaration//GEN-END:variables
}