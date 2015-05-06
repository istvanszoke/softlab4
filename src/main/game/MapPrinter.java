package game;

import buff.Buff;
import feedback.NoFeedbackException;
import feedback.Result;
import field.*;

public class MapPrinter {
    public static void print(Map map, int cellWidth) {
        FieldPrinter p = new FieldPrinter();
        StringBuilder sb = new StringBuilder();

        sb.append(rowSeparator(map.getWidth(), cellWidth)).append("\n");
        for (int row = 0; row < map.getHeight(); ++row) {
            for (int col = 0; col < map.getWidth(); ++col) {
                Field f = map.get(row, col);

                f.accept(p);
                sb.append("|").append(p.output);
                int currentWidth =  1;

                if (f.getAgent() != null) {
                    sb.append(f.getAgent());
                } else {
                    sb.append(" ");
                }
                ++currentWidth;

                for (Buff b : f.getBuffs()) {
                    sb.append(b);
                    ++currentWidth;
                }

                while (currentWidth < cellWidth) {
                    sb.append(" ");
                    ++currentWidth;
                }
                sb.append("|");
            }
            sb.append("\n").append(rowSeparator(map.getWidth(), cellWidth)).append("\n");
        }
        System.out.println(sb.toString());
    }

    private static class FieldPrinter implements FieldVisitor {
        public String output = "INVALID";

        @Override
        public void visit(FieldCell element) {
            output = " ";
        }

        @Override
        public void visit(EmptyFieldCell element) {
            output = "#";
        }

        @Override
        public void visit(FinishLineFieldCell element) {
            output = "$";
        }

        @Override
        public Result getResult() throws NoFeedbackException {
            return null;
        }
    }

    private static String rowSeparator(int width, int cellWidth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width * (cellWidth + 2); ++i) {
            sb.append("-");
        }
        return sb.toString();
    }
}
