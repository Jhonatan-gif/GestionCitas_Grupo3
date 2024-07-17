package Vista;

import Controlador.ProcesoCita;
import Controlador.ProcesoConsultorio;
import Controlador.ProcesoPaciente;
import Modelo.Cita;
import Modelo.Consultorio;
import Modelo.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.StringTokenizer;


public class VistaCitas extends javax.swing.JFrame {

    
    Limpiar_txt lt = new Limpiar_txt();
    
    private String ruta_txt = "Citas.txt"; 
    private String ruta2_txt= "Consultorio.txt";
    
    Paciente p;
    Cita c;
    Consultorio m;
    
    ProcesoPaciente rp;
    ProcesoCita rc;
    ProcesoConsultorio mp;
    
    int clic_tabla;
    
    public VistaCitas() {
        initComponents();
        rp = new ProcesoPaciente();
        rc=new ProcesoCita();
        mp=new ProcesoConsultorio();
        
        cargar_consul();
        try{
            
            cargar_txt();
            
            listarRegistro();
            cargar_comb();
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }
    }
    public void cargar_comb(){
        
        for(int j = 0; j < mp.cantidadRegistro(); j++){
                    m=mp.obtenerRegistro(j);
                    cmbC.addItem(m.getDescripcion());                    
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
                p=new Paciente();
                p.setId(Integer.parseInt(st.nextToken()));
                p.setNombre(st.nextToken());
                p.setApellidoPaterno(st.nextToken());
                p.setApellidoMaterno(st.nextToken());
                p.setFechaNac(st.nextToken());
                rp.agregarRegistro(p);
                c=new Cita();
                c.setId(Integer.parseInt(st.nextToken()));
                c.setHora(st.nextToken());
                c.setDesripcion(st.nextToken());
                
                int idConsultorio=(Integer.parseInt(st.nextToken()));
                int cod = mp.buscaCodigo(idConsultorio);
            
               
                c.setConsultorio(mp.obtenerRegistro(cod));
                rc.agregarRegistro(c);
                
              
            }
            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void cargar_consul(){
        File ruta = new File(ruta2_txt);
        try{
            
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                m=new Consultorio();
                m.setIdConsultorio(Integer.parseInt(st.nextToken()));
                m.setDescripcion(st.nextToken());
                mp.agregarRegistro(m);
                
              
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
                c=rc.obtenerRegistro(i);
                pw.println(String.valueOf(p.getId()+", "+p.getNombre()+", "+p.getApellidoPaterno()+", "+p.getApellidoMaterno()+", "+p.getFechaNac()+","+c.getId()+","+c.getHora()+","+c.getDesripcion()+","+c.getConsultorio().getIdConsultorio()));
            }
             pw.close();
            
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void ingresarRegistro(){
        int val=0,val2=0;
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre()== null)mensaje("Ingresar Nombre del Paciente");
            else if(leerApellidoPaterno()== null)mensaje("Ingresar Apellido paterno del paciente");
            else if(leerApellidoMaterno()== null)mensaje("Ingresar Apellido materno del paciente");
            else if(leerFechaNac()== null)mensaje("Ingresar fecha de nacimiento");
            else if(leerCodigoCita()== -666)mensaje("Ingresar codigo cita");
            else if(leerHora()== null)mensaje("Ingresar hora");
            else if(leerDescripcion()== null)mensaje("Ingresar descripcion");
            else if(leerConsultorios()== null)mensaje("Ingresar consultorio");
            else{
                p = new Paciente(leerCodigo(), leerNombre(),leerApellidoPaterno(),leerApellidoMaterno(),leerFechaNac());
                if(rp.buscaCodigo(p.getId())!= -1)mensaje("Este codigo ya existe");
                else 
                {
                    val=1;
                }
                String cons = leerConsultorios();
                int cod = mp.buscaDEs(cons);//en duda
                c = new Cita(leerCodigoCita(), leerHora(),leerDescripcion(),mp.obtenerRegistro(cod));
                if(rc.buscaCodigo(c.getId())!= -1)mensaje("Este codigo ya existe");
                else val2=1;
                int val3=1;
                for(int j = 0; j < rc.cantidadRegistro(); j++){
                    c=rc.obtenerRegistro(j);
                    if(c.getConsultorio().getDescripcion().equals(leerConsultorios()) && c.getHora().equals(leerHora())){
                        val3=2;
                    }
                }
                if(val3==2){
                    mensaje("CONSULTORIO CON HORA YA ESTA ASIGNADO");
                }
                
                if(val==1 && val2==1 && val3==1){
                    
                    rp.agregarRegistro(p);
                    rc.agregarRegistro(c);
                    grabar_txt();
                    listarRegistro();
                }
                else
                {
                    mensaje("ERROR AL REGISTRAR");
                }
                
                lt.limpiar_texto(panel); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
    public void modificarRegistro(){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre()== null)mensaje("Ingresar Nombre del Paciente");
            else if(leerApellidoPaterno()== null)mensaje("Ingresar Apellido paterno del paciente");
            else if(leerApellidoMaterno()== null)mensaje("Ingresar Apellido materno del paciente");
            else if(leerFechaNac()== null)mensaje("Ingresar fecha de nacimiento");
            else if(leerCodigoCita()== -666)mensaje("Ingresar codigo cita");
            else if(leerHora()== null)mensaje("Ingresar hora");
            else if(leerDescripcion()== null)mensaje("Ingresar descripcion");
            else if(leerConsultorios()== null)mensaje("Ingresar consultorio");
            else{
                int codigo = rp.buscaCodigo(leerCodigo());
                p = new Paciente(leerCodigo(), leerNombre(),leerApellidoPaterno(),leerApellidoMaterno(),leerFechaNac());
                
                if(codigo == -1)rp.agregarRegistro(p);
                else rp.modificarRegistro(codigo, p);
                
                int codigo2 = rc.buscaCodigo(leerCodigoCita());
                int cod = mp.buscaDEs(leerConsultorios());
                c = new Cita(leerCodigoCita(), leerHora(),leerDescripcion(),mp.obtenerRegistro(cod));
                
                if(codigo2 == -1)rc.agregarRegistro(c);
                else rc.modificarRegistro(codigo, c);
                
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
                int codigo2 = rc.buscaCodigo(leerCodigoCita());
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","Si/No",0);
                    if(s == 0){
                        rp.eliminarRegistro(codigo);
                        rc.eliminarRegistro(codigo2);
                        
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
        
        dt.addColumn("Codigo Paciente");
        dt.addColumn("Nombre");
        dt.addColumn("Apellido Paterno");
        dt.addColumn("Apellido Materno");
        dt.addColumn("Fecha Nacimiento");
        dt.addColumn("Codigo Cita");
        dt.addColumn("Hora Cita");
        dt.addColumn("Descripcion");
        dt.addColumn("Consultorio");
        
        tabla.setDefaultRenderer(Object.class, new imgTabla());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < rp.cantidadRegistro(); i++){
            p = rp.obtenerRegistro(i);
            fila[0] = p.getId();
            fila[1] = p.getNombre();
            fila[2] = p.getApellidoPaterno();
            fila[3] = p.getApellidoMaterno();
            fila[4] = p.getFechaNac();
            
            c=rc.obtenerRegistro(i);
        
            fila[5] = c.getId();
            fila[6] = c.getHora();
            fila[7] = c.getDesripcion();
            fila[8] = c.getConsultorio().getDescripcion();
            
            
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
    public String leerApellidoPaterno(){
        try{
            String ApellidoPaterno = txtApellidoPaterno.getText().trim().replace(" ", "_");
            return ApellidoPaterno;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String leerApellidoMaterno(){
        try{
            String ApellidoMaterno = txtApellidoMaterno.getText().trim();
            return ApellidoMaterno;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String leerFechaNac(){
        try{
            String FechaNac = txtFechaNacimiento.getText().trim();
            return FechaNac;
        }catch(Exception ex){
            return null;
        }
    }
    
    public int leerCodigoCita(){
        try{
            int codigo = Integer.parseInt(txtCodigoCita.getText().trim());
            return codigo;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leerHora(){
        try{
            String hora = txtHora.getText().trim();
            return hora;
        }catch(Exception ex){
            return null;
        }
    }
    
    public String leerDescripcion(){
        try{
            String descripcion = txtDescripcion.getText().trim();
            return descripcion;
        }catch(Exception ex){
            return null;
        }
    }
    public String leerConsultorios(){
        try{
            String consult =cmbC.getSelectedItem().toString();
            return consult;
        }catch(Exception ex){
            return null;
        }
    }
    
    
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
    

    @SuppressWarnings("unchecked")

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
        txtApellidoPaterno = new javax.swing.JTextField();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        txtApellidoMaterno = new javax.swing.JTextField();
        txtFechaNacimiento = new javax.swing.JTextField();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        txtHora = new javax.swing.JTextField();
        jLabel11 = new JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel12 = new JLabel();
        jLabel2 = new JLabel();
        txtCodigoCita = new javax.swing.JTextField();
        cmbC = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jButton1.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 14));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel4.setText("Nombre ");

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel1.setText("Codigo:");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/limpiar.png")));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icn_citas.png")));

        txtApellidoPaterno.setFont(new java.awt.Font("SansSerif", 0, 14));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel7.setText("Apellido Paterno");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel8.setText("Apellido Materno");

        txtApellidoMaterno.setFont(new java.awt.Font("SansSerif", 0, 14));

        txtFechaNacimiento.setFont(new java.awt.Font("SansSerif", 0, 14));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel9.setText("Fecha Nacimiento");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel10.setText("Hora cita medica");

        txtHora.setFont(new java.awt.Font("SansSerif", 0, 14));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel11.setText("Descripcion");

        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 14));

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel12.setText("Consultorio");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14));
        jLabel2.setText("Codigo cita:");

        txtCodigoCita.setFont(new java.awt.Font("SansSerif", 0, 14));

        cmbC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Escoja Opcion" }));

        jButton4.setFont(new java.awt.Font("Arial Black", 0, 14));
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
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4))
                        .addGap(37, 37, 37)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(67, 67, 67))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jButton1))
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(78, 78, 78)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addGap(37, 37, 37)
                                        .addComponent(txtCodigoCita, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cmbC, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 95, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(txtApellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtCodigoCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(cmbC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton4))
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
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
        String apellidoPaterno = ""+tabla.getValueAt(clic_tabla, 2);
        String apellidoMaterno = ""+tabla.getValueAt(clic_tabla, 3);
        String fecha = ""+tabla.getValueAt(clic_tabla, 4);
        int codigoCita = (int)tabla.getValueAt(clic_tabla, 5);
        String hora = ""+tabla.getValueAt(clic_tabla, 6);
        String descripcion = ""+tabla.getValueAt(clic_tabla, 7);
        String consulto = ""+tabla.getValueAt(clic_tabla, 8);
        
        txtCodigo.setText(String.valueOf(codigo));
        txtNombre.setText(nombre);
        txtApellidoPaterno.setText(apellidoPaterno);
        txtApellidoMaterno.setText(apellidoMaterno);
        txtFechaNacimiento.setText(fecha);
        txtCodigoCita.setText(String.valueOf(codigoCita));
        txtHora.setText(hora);
        txtDescripcion.setText(descripcion);
        cmbC.setSelectedItem(consulto);
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
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(VistaCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCitas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCitas().setVisible(true);
            }
        });
    }


    private javax.swing.JComboBox<String> cmbC;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCita;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNombre;
}
