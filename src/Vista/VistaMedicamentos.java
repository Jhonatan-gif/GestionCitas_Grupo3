package Vista;

import Controlador.ProcesoMedicamento;
import Modelo.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.StringTokenizer;


public class VistaMedicamentos extends javax.swing.JFrame {

    
    Limpiar_txt lt = new Limpiar_txt();
    
    private String ruta_txt = "Medicamentos.txt"; 
    
    Medicamento p;
    ProcesoMedicamento rp;
    
    int clic_tabla;
    public VistaMedicamentos() {
        initComponents();
        rp = new ProcesoMedicamento();
        
        try{
            cargar_txt();
            listarRegistro();
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }    
    }
     public void cargar_txt(){
        File ruta = new File(ruta_txt);
        try{
            
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                p=new Medicamento();
                p.setId(Integer.parseInt(st.nextToken()));
                p.setNombre(st.nextToken());
                p.setPrincipio(st.nextToken());
                p.setTipo(st.nextToken());
                p.setGramaje(Double.parseDouble(st.nextToken()));
                p.setStock(Integer.parseInt(st.nextToken()));
                rp.agregarRegistro(p);
            }
            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void grabar_txt(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(ruta_txt);
            pw = new PrintWriter(fw);
            
            for(int i = 0; i < rp.cantidadRegistro(); i++){
                p = rp.obtenerRegistro(i);
                pw.println(String.valueOf(p.getId()+", "+p.getNombre()+", "+p.getPrincipio()+", "+p.getTipo()+", "+p.getGramaje()+","+p.getStock()));
            }
             pw.close();
            
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void ingresarRegistro(){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre()== null)mensaje("Ingresar Nombre Comercial");
            else if(leerPrincipio()== null)mensaje("Ingresar Principio Activo");
            else if(leerTipo()== null)mensaje("Ingresar Tipo");
            else if(leerGramaje()== -666)mensaje("Ingresar Gramaje");
            else if(leerStock()== -666)mensaje("Ingresar Stock");
            else{
                p = new Medicamento(leerCodigo(), leerNombre(),leerPrincipio(),leerTipo(),leerGramaje(),leerStock());
                if(rp.buscaCodigo(p.getId())!= -1)mensaje("Este codigo ya existe");
                else rp.agregarRegistro(p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void modificarRegistro(){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre()== null)mensaje("Ingresar Nombre Comercial");
            else if(leerPrincipio()== null)mensaje("Ingresar Principio Activo");
            else if(leerTipo()== null)mensaje("Ingresar Tipo");
            else if(leerGramaje()== -666)mensaje("Ingresar Gramaje");
            else if(leerStock()== -666)mensaje("Ingresar Stock");
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                p = new Medicamento(leerCodigo(), leerNombre(),leerPrincipio(),leerTipo(),leerGramaje(),leerStock());
                
                if(codigo == -1)rp.agregarRegistro(p);
                else rp.modificarRegistro(codigo, p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void eliminarRegistro(){
        try{
            if(leerCodigo() == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","Si/No",0);
                    if(s == 0){
                        rp.eliminarRegistro(codigo);
                        
                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(panel);
                    }
                }
                
                
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre Comercial");
        dt.addColumn("Principio Activo");
        dt.addColumn("Tipo");
        dt.addColumn("Gramaje");
        dt.addColumn("Stock");
        
        tabla.setDefaultRenderer(Object.class, new imgTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < rp.cantidadRegistro(); i++){
            p = rp.obtenerRegistro(i);
            fila[0] = p.getId();
            fila[1] = p.getNombre();
            fila[2] = p.getPrincipio();
            fila[3] = p.getTipo();
            fila[4] = p.getGramaje();
            fila[5] = p.getStock();
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(60);
    }
    
    public int leerCodigo(){
        try{
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            return codigo;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leerNombre(){
        try{
            String nombre = txtNombre.getText().trim().replace(" ", "_");
            return nombre;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerPrincipio(){
        try{
            String principio = txtPrincipio.getText().trim().replace(" ", "_");
            return principio;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String leerTipo(){
        try{
            String tipo = txtTipo.getText().trim();
            return tipo;
        }catch(Exception ex){
            return null;
        }
    }
    
    public double leerGramaje(){
        try{
            double gramaje = Double.parseDouble(txtGramaje.getText().trim());
            return gramaje;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public int leerStock(){
        try{
            int stock = Integer.parseInt(txtStock.getText().trim());
            return stock;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        txtPrincipio = new javax.swing.JTextField();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        txtTipo = new javax.swing.JTextField();
        txtGramaje = new javax.swing.JTextField();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        txtStock = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(204, 204, 204));

        tabla.setModel(new DefaultTableModel(
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
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jButton1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Nombre Comercial");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setText("Codigo:");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/limpiar.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icn_medica.png"))); // NOI18N

        txtPrincipio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setText("Principio Activo");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setText("Tipo");

        txtTipo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtGramaje.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setText("Gramaje");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setText("Stock");

        txtStock.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jButton4.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(108, 108, 108)
                                .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(52, 52, 52))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(37, 37, 37)))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(txtGramaje, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPrincipio, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 130, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(67, 67, 67))))))
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrincipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGramaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(8, 8, 8))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(62, 62, 62)))
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(22, 22, 22)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(jButton5))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked

        clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int codigo = (int)tabla.getValueAt(clic_tabla, 0);
        String nombre = ""+tabla.getValueAt(clic_tabla, 1);
        String principio = ""+tabla.getValueAt(clic_tabla, 2);
        String tipo = ""+tabla.getValueAt(clic_tabla, 3);
        double gramaje = (double)tabla.getValueAt(clic_tabla, 4);
        int stock = (int)tabla.getValueAt(clic_tabla, 5);
        

        txtCodigo.setText(String.valueOf(codigo));
        txtNombre.setText(String.valueOf(nombre));
        txtPrincipio.setText(principio);
        txtTipo.setText(tipo);
        txtGramaje.setText(String.valueOf(gramaje));
        txtStock.setText(String.valueOf(stock));
        

        try{
            JLabel lbl = (JLabel)tabla.getValueAt(clic_tabla, 4);
        }catch(Exception ex){
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.ingresarRegistro();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        this.modificarRegistro();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        this.eliminarRegistro();
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        Limpiar_txt lp = new Limpiar_txt();
        lp.limpiar_texto(panel);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaMedicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaMedicamentos().setVisible(true);
            }
        });
    }


    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtGramaje;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrincipio;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtTipo;

}
