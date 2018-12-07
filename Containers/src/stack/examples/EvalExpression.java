package stack.examples;


/*
* operators: +,-,*,/,(,),#
*
* */
public class EvalExpression {

    public static double evaluate(String expression){
        String[] elements = expression.split(" ");
        for (String e: elements){
            System.out.println(e);
        }
        return 0;
    }
    public static void main(String[] args){
        evaluate("1+3+3-2/1-4");
    }
}
