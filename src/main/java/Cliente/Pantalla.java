package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JTextField;

public class Pantalla extends javax.swing.JFrame {

    public Pantalla() {
        initComponents();

    }
    
    public void enviar(int envio) {
        Socket cliente; // Socket para la comunicación con el servidor
        DataInputStream entradaC; // Stream de entrada para recibir datos del servidor
        DataOutputStream salidaC; // Stream de salida para enviar datos al servidor
        
        try {
            // Conexión al servidor en el puerto 5000 del localhost
            cliente = new Socket("localhost", 5000); 
            entradaC = new DataInputStream(cliente.getInputStream()); // stream de entrada
            salidaC = new DataOutputStream(cliente.getOutputStream()); // stream de salida
            
            // Envía el código de envío al servidor
            salidaC.writeInt(envio);
            // Recibe la respuesta numérica del servidor
            int respuesta = entradaC.readInt();
            // Recibe la respuesta textual del servidor
            String respuestaTexto = entradaC.readUTF();
            
            // Actualiza los componentes de la interfaz gráfica según la respuesta del servidor
            switch(envio){
                case 1: getPasajerosM().setText(String.valueOf(respuesta)); break;
                case 2: getPasajerosB().setText(String.valueOf(respuesta)); break;
                case 3: getHangarTextFieldM().setText(String.valueOf(respuesta)); break;
                case 4: getHangarTextFieldB().setText(String.valueOf(respuesta)); break;
                case 5: getTallerTextFieldM().setText(String.valueOf(respuesta)); break;
                case 6: getTallerTextFieldB().setText(String.valueOf(respuesta)); break;
                case 7: getEstacioanmientoTextFieldM().setText(String.valueOf(respuesta)); break;
                case 8: getEstacioanmientoTextFieldB().setText(String.valueOf(respuesta)); break;      
                case 9: getRodajeTextFieldM().setText(String.valueOf(respuesta)); break;
                case 10: getRodajeTextFieldB().setText(String.valueOf(respuesta)); break;
                case 11: getAerBMTextField12().setText(String.valueOf(respuestaTexto)); break;
                case 12: getAerMBTextField11().setText(String.valueOf(respuestaTexto)); 
            }
            
            // Cierra los streams y la conexión con el servidor
            entradaC.close();
            salidaC.close();
            cliente.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        pasajerosB = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        aerMBTextField11 = new javax.swing.JTextField();
        aerBMTextField12 = new javax.swing.JTextField();
        hangarTextFieldM = new javax.swing.JTextField();
        tallerTextFieldM = new javax.swing.JTextField();
        estacioanmientoTextFieldM = new javax.swing.JTextField();
        rodajeTextFieldM = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pasajerosM = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        hangarTextFieldB = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        tallerTextFieldB = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        estacioanmientoTextFieldB = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        rodajeTextFieldB = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel19.setText("Pista 3");

        jLabel20.setText("Pista 4");

        pasajerosB.setEditable(false);

        jButton16.setText("Cerrar");

        jLabel21.setText("Aerovía Madrid-Barcelona");

        jLabel22.setText("Aerovía Barcelona-Madrid");

        aerMBTextField11.setEditable(false);

        aerBMTextField12.setEditable(false);

        hangarTextFieldM.setEditable(false);

        tallerTextFieldM.setEditable(false);
        tallerTextFieldM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tallerTextFieldMActionPerformed(evt);
            }
        });

        estacioanmientoTextFieldM.setEditable(false);

        rodajeTextFieldM.setEditable(false);

        jLabel8.setText("Pista 1");

        jLabel9.setText("Pista 2");

        jLabel10.setText("Pista 3");

        jLabel11.setText("Pista 4");

        jButton1.setText("Cerrar");

        jButton2.setText("Abrir");

        jLabel1.setText("Aeropuerto de Madrid");

        jLabel2.setText("Aeropuerto de Barcelona");

        jLabel3.setText("Nº de pasajeros en Aeropuerto:");

        jLabel4.setText("Nº de aviones en Hangar: ");

        jLabel5.setText("Nº de aviones en Taller: ");

        jLabel6.setText("Nº de aviones en Área Estacionamiento: ");

        jLabel7.setText("Nº de aviones en Área Rodaje: ");

        pasajerosM.setEditable(false);
        pasajerosM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasajerosMActionPerformed(evt);
            }
        });

        jButton3.setText("Abrir");

        jButton4.setText("Cerrar");

        jButton5.setText("Abrir");

        jButton6.setText("Cerrar");

        jButton7.setText("Abrir");

        jButton8.setText("Cerrar");

        jButton9.setText("Abrir");

        jButton10.setText("Abrir");

        hangarTextFieldB.setEditable(false);

        jButton11.setText("Cerrar");

        jLabel12.setText("Nº de aviones en Aeropuerto:");

        tallerTextFieldB.setEditable(false);

        jButton12.setText("Abrir");

        jLabel13.setText("Nº de aviones en Hangar: ");

        estacioanmientoTextFieldB.setEditable(false);

        jButton13.setText("Cerrar");

        jLabel14.setText("Nº de aviones en Taller: ");

        rodajeTextFieldB.setEditable(false);

        jButton14.setText("Abrir");

        jLabel15.setText("Nº de aviones en Área Estacionamiento: ");

        jLabel16.setText("Pista 1");

        jButton15.setText("Cerrar");

        jLabel17.setText("Nº de aviones en Área Rodaje: ");

        jLabel18.setText("Pista 2");

        jButton17.setText("Consultar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Consultar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pasajerosM))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(hangarTextFieldM))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tallerTextFieldM))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(estacioanmientoTextFieldM))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(rodajeTextFieldM))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton3))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2)))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton5))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton7))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(160, 160, 160)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(140, 140, 140))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(pasajerosB))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(hangarTextFieldB))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tallerTextFieldB))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(estacioanmientoTextFieldB))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(rodajeTextFieldB))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton10))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton9)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton12))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel20)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton14))))))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aerMBTextField11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                            .addComponent(aerBMTextField12)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(414, 414, 414)
                                .addComponent(jLabel21))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(410, 410, 410)
                                .addComponent(jLabel22)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(pasajerosM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(hangarTextFieldM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tallerTextFieldM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(estacioanmientoTextFieldM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(rodajeTextFieldM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton6)
                            .addComponent(jButton5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jButton4)
                            .addComponent(jButton3)
                            .addComponent(jButton8)
                            .addComponent(jButton7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(pasajerosB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(hangarTextFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(tallerTextFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(estacioanmientoTextFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(rodajeTextFieldB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(jButton16)
                            .addComponent(jButton9)
                            .addComponent(jButton13)
                            .addComponent(jButton12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel20)
                            .addComponent(jButton11)
                            .addComponent(jButton10)
                            .addComponent(jButton15)
                            .addComponent(jButton14))))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18)
                    .addComponent(jButton17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aerMBTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addGap(8, 8, 8)
                .addComponent(aerBMTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pasajerosMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasajerosMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasajerosMActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        enviar(1);
        enviar(3);
        enviar(5);
        enviar(7);
        enviar(9);
        enviar(11);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void tallerTextFieldMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tallerTextFieldMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tallerTextFieldMActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        enviar(2);
        enviar(4);
        enviar(6);
        enviar(8);
        enviar(10);
        enviar(12);
    }//GEN-LAST:event_jButton18ActionPerformed

 
    public JTextField getPasajerosB() {
        return pasajerosB;
    }

    public JTextField getPasajerosM() {
        return pasajerosM;
    }

    public JTextField getAerBMTextField12() {
        return aerBMTextField12;
    }

    public JTextField getAerMBTextField11() {
        return aerMBTextField11;
    }

    public JTextField getEstacioanmientoTextFieldB() {
        return estacioanmientoTextFieldB;
    }

    public JTextField getEstacioanmientoTextFieldM() {
        return estacioanmientoTextFieldM;
    }

    public JTextField getHangarTextFieldB() {
        return hangarTextFieldB;
    }

    public JTextField getHangarTextFieldM() {
        return hangarTextFieldM;
    }

    public JTextField getRodajeTextFieldB() {
        return rodajeTextFieldB;
    }

    public JTextField getRodajeTextFieldM() {
        return rodajeTextFieldM;
    }

    public JTextField getTallerTextFieldB() {
        return tallerTextFieldB;
    }

    public JTextField getTallerTextFieldM() {
        return tallerTextFieldM;
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aerBMTextField12;
    private javax.swing.JTextField aerMBTextField11;
    private javax.swing.JTextField estacioanmientoTextFieldB;
    private javax.swing.JTextField estacioanmientoTextFieldM;
    private javax.swing.JTextField hangarTextFieldB;
    private javax.swing.JTextField hangarTextFieldM;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField pasajerosB;
    private javax.swing.JTextField pasajerosM;
    private javax.swing.JTextField rodajeTextFieldB;
    private javax.swing.JTextField rodajeTextFieldM;
    private javax.swing.JTextField tallerTextFieldB;
    private javax.swing.JTextField tallerTextFieldM;
    // End of variables declaration//GEN-END:variables
}
