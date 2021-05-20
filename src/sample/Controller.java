package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class Controller {

    @FXML
    private TextField txtOperation;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

    @FXML
    private Button btnAdd, btnSub, btnMul, btnDiv, btnEqual, btnClr;

    @FXML
    private Button btnOpenP, btnCloseP;

    @FXML
    public void handler(ActionEvent event){
        Button b = (Button) event.getSource();
        String text = b.getText();

        if(text.equals("=")){
            Calculate result = new Calculate();
            String s = result.result(txtOperation.getText());

            txtOperation.setText(s);
        }
        else if(text.equals("C")){
            txtOperation.setText("");
        }
        else{
            txtOperation.setText(txtOperation.getText() + text);
        }

    }



}
