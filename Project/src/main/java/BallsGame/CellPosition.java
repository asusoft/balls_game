package BallsGame;

public class CellPosition {
    //Позиция колонки
    private int x;

    //Позиция строки
    private int y;

    //Конструктор
    CellPosition(int x, int y)
    {
        if (x < 0 || y < 0)
        {
            throw new IllegalArgumentException();
        }

        this.x = x;
        this.y = y;
    }

    //Получить позицию колонки
    public int X(){
        return x;
    }

    //Получить позицию строки
    public int Y(){
        return y;
    }

    //Проверка эквивалентности
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CellPosition))
        {
            return false;
        }

        CellPosition pos = (CellPosition)obj;

        return (pos.x == this.x && pos.y == this.y);
    }

    @Override
    public int hashCode() {
        return y * 100 + x;
    }
}
