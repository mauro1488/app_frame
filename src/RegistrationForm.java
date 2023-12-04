import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLStreamHandlerFactory;
import java.nio.file.attribute.UserPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog{
    private JTextField tfNombre;
    private JTextField tfEmail;
    private JTextField tfTelefono;
    private JTextField tfDireccion;
    private JPasswordField cfContraseña;
    private JPasswordField cfConfirmarcontraseña;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JPanel Panelderegistro;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("crea una nueva cuenta");
        setContentPane(Panelderegistro);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setVisible(true);


        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String nombre = tfNombre.getText();
        String email = tfEmail.getText();
        String telefono = tfTelefono.getText();
        String direccion = tfDireccion.getText();
        String contraseña = String.valueOf(cfContraseña.getPassword());
        String confirmarContraseña = String.valueOf(cfConfirmarcontraseña.getPassword());

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "por favor ingrese todos los campos",
                     "intentar otra vez" ,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (! contraseña.equals(confirmarContraseña)){
            JOptionPane.showMessageDialog( this,
                    "confirmar contraseña no coincide", "" +
                            "intentar otra vez" ,
                    JOptionPane.ERROR_MESSAGE);
            return;

        }
          user = addUserToDatabase(nombre, email, telefono, direccion, contraseña);
        if (user != null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog( this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);

        }

    }


    public UserPrincipal user;
    private <User> User addUserToDatabase(String nombre, String email, String telefono, String direccion, String contraseña) {
        User user = null;

        String DB_URL = "jdbc:mysql://localhost:3306/Java_curso?serverTimezone=UTC";
        String USERNAME = "root";
        String PASSWORD = "admin";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (nombre, email, telefono, direccion, contraseña)VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, telefono);
            preparedStatement.setString(4, direccion);
            preparedStatement.setString(5, contraseña);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows >0){
                User user = new User();
                user.nombre = nombre;
                user.email = email;
                user.telefono= telefono;
                user.direccion = direccion;
                user.contraseña = contraseña;


            }

            stmt.close();
            conn.close();





        }catch (Exception e){
            e.printStackTrace();
        }
        return user;

    }

    public static void main(String[] args) {
        RegistrationForm myform = new RegistrationForm(null);
        User user = myform.user;
        if (user != null){
            System.out.println("Successful registration of:" + user.name);
            
        }
        else{
            boolean Registration = false;
            Object cancelar = null;
            System.out.println(Registration cancelar);
        }
    }
}
