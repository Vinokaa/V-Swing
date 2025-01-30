package VSwing.Exporter;

public class OutOfDrawingBoardException extends Exception{
    public OutOfDrawingBoardException(String msg){
        super(msg);
    }

    public OutOfDrawingBoardException(){
        super();
    }
}
