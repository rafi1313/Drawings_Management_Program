package model;

public class Drawing {
    //        id_drawings, drawing_name, drawing_num, width, height, individual_rate, paid
    private int id_drawings;
    private String drawing_name;
    private String drawing_num;
    private int width;
    private int height;
    private int individual_rate;
    private String paid;

    public Drawing() {   }

    public Drawing(int id_drawings, String drawing_name, String drawing_num, int width, int height, int individual_rate, String paid) {
        this.id_drawings = id_drawings;
        this.drawing_name = drawing_name;
        this.drawing_num = drawing_num;
        this.width = width;
        this.height = height;
        this.individual_rate = individual_rate;
        this.paid = paid;
    }

    public int getId_drawings() {
        return id_drawings;
    }

    public void setId_drawings(int id_drawings) {
        this.id_drawings = id_drawings;
    }

    public String getDrawing_name() {
        return drawing_name;
    }

    public void setDrawing_name(String drawing_name) {
        this.drawing_name = drawing_name;
    }

    public String getDrawing_num() {
        return drawing_num;
    }

    public void setDrawing_num(String drawing_num) {
        this.drawing_num = drawing_num;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getIndividual_rate() {
        return individual_rate;
    }

    public void setIndividual_rate(int individual_rate) {
        this.individual_rate = individual_rate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
