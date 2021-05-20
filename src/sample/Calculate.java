package sample;

import java.util.ArrayList;

public class Calculate{
    //Check if a character is an operator
    static boolean isOperator(char c){
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    //Analyze string and add numbers and operators to the corresponding lists
    static void parseString(String s, ArrayList<Double> numbers, ArrayList<Integer> operators){
        int actual = 0;
        int start = 0;

        while(actual < s.length()){
            char c = s.charAt(actual);

            //Check if current char is an operator
            if(isOperator(c)){
                operators.add(actual);
                actual++;
            }
            else if(Character.isDigit(c)  || c == '.'){
                //If it is a digit go to the last number before next operator
                while(actual < s.length() && (Character.isDigit(s.charAt(actual))  || s.charAt(actual) == '.'))
                    actual++;

                //Store number into a new String
                String num = new String();
                for(int i = start; i < actual; i++)
                    num += s.charAt(i);

                numbers.add(Double.valueOf(num));
            }
            else{
                //Go to next character if a parenthesis is found
                actual++;
            }

            start = actual;		// Update a "start"
        }
    }

    //Calculate the result between x and y given an operator op
    static double calculateOperations(char op, double x, double y){
        if(op == '/')
            return x / y;
        if(op == '*')
            return x * y;
        if(op == '-')
            return x - y;
        if(op == '+')
            return x + y;
        else
            return 0;
    }

    //Make operations inside parentheses and save changes inside ArrayLists
    static void operationInParenthesis(String s, char operation, ArrayList<Double> numbers, ArrayList<Integer> operators, int start, int end){
        //Traverse operators ArrayLis
        for(int i = 0; i < operators.size(); i++){
            //Make operations between start and end limits
            if(operators.get(i) > start && operators.get(i) < end){
                if(s.charAt(operators.get(i)) == operation){
                    double res = calculateOperations(operation, numbers.get(i), numbers.get(i + 1));	//Calculate result of operation
                    operators.remove(i);					//Remove operator that was just used
                    numbers.remove(i);
                    numbers.remove(i);						//Remove numbers used on the operation
                    numbers.add(i, res);					//Add operation result

                    i--;
                }
            }
        }
    }

    //Calculate the result of the operation inside a pair of parentheses knowing the start and the end indices
    static void resultInParenthesis(String s, ArrayList<Double> numbers, ArrayList<Integer> operators, int start, int end){
        int numOperators = 1;			//Count number of operators inside the parentheses

        //Make operations until there is no operators inside the parentheses
        while(numOperators != 0){
            numOperators = 0;

            for (int i = start; i < end; i++) {
                for (int opC = 0; opC < operators.size(); opC++) {
                    if (operators.get(opC) == i)
                        numOperators++;
                }
            }

            //Make the operations in order
            operationInParenthesis(s, '/', numbers, operators, start, end);
            operationInParenthesis(s, '*', numbers, operators, start, end);
            operationInParenthesis(s, '-', numbers, operators, start, end);
            operationInParenthesis(s, '+', numbers, operators, start, end);
        }
    }

    //Analyze string and make the operations from inside the parentheses to outside
    static void checkParenthesis(String s, ArrayList<Double> numbers, ArrayList<Integer> operators){
        for(int i = 0; i < s.length(); i++){
            int openCount = 0;
            int closeCount = 0;

            // Search for the first close parenthesis
            if(s.charAt(i) == ')'){
                int j;

                //Find the respective open parenthesis
                for(j = i; j > 0; j--){
                    if(s.charAt(j) == '(')
                        openCount++;
                    if(s.charAt(j) == ')')
                        closeCount++;
                    if(closeCount == openCount)
                        break;
                }

                //Once a pair of parentheses are found, the result inside of it is calculated
                resultInParenthesis(s, numbers, operators, j, i);
            }
        }
    }

    //Calculate the final result
    static void result(String s, ArrayList<Double> numbers, ArrayList<Integer> operators){
        //Make operations in order until there is just one number in the "numbers" list
        while (numbers.size() != 1) {
            operationInParenthesis(s, '/', numbers, operators, 0, s.length());
            operationInParenthesis(s, '*', numbers, operators, 0, s.length());
            operationInParenthesis(s, '-', numbers, operators, 0, s.length());
            operationInParenthesis(s, '+', numbers, operators, 0, s.length());
        }
    }

    public String result(String s){
        ArrayList<Integer> operators = new ArrayList<>();
        ArrayList<Double> numbers = new ArrayList<>();

        parseString(s, numbers, operators);
        checkParenthesis(s, numbers, operators);
        result(s, numbers, operators);

        String res = String.format("%5f", numbers.get(0));
        //System.out.println(String.format("%5f", numbers.get(0)));
        return res;
    }
}


