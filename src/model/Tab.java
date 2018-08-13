package model;

public class Tab {
    private String tab1;
    private String tab2;
    private String tab3;
    public Tab(String tab1, String tab2, String tab3) {
        this.tab1 = tab1;
        this.tab2 = tab2;
        this.tab3 = tab3;
    }

    public Tab() {
    }

    public String getTab1() {
        return tab1;
    }

    public void setTab1(String tab1) {
        this.tab1 = tab1;
    }

    public String getTab2() {
        return tab2;
    }

    public void setTab2(String tab2) {
        this.tab2 = tab2;
    }

    public String getTab3() {
        return tab3;
    }

    public void setTab3(String tab3) {
        this.tab3 = tab3;
    }
}
