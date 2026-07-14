package com.group.coinquest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u0006\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u001c\u0010\u0015\u001a\u00020\u000f2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000f0\u0017H\u0002J\u001c\u0010\u0018\u001a\u00020\u000f2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J\u0018\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/group/coinquest/AnalyticsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "barChart", "Lcom/github/mikephil/charting/charts/BarChart;", "db", "Lcom/group/coinquest/AppDatabase;", "endDate", "", "pieChart", "Lcom/github/mikephil/charting/charts/PieChart;", "startDate", "totalText", "Landroid/widget/TextView;", "loadData", "", "userId", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "pickDate", "callback", "Lkotlin/Function1;", "setupBarChart", "dataMap", "", "", "setupPieChart", "income", "expense", "app_debug"})
public final class AnalyticsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.group.coinquest.AppDatabase db;
    private com.github.mikephil.charting.charts.BarChart barChart;
    private com.github.mikephil.charting.charts.PieChart pieChart;
    private android.widget.TextView totalText;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String startDate = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String endDate = "";
    
    public AnalyticsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadData(int userId) {
    }
    
    private final void setupPieChart(double income, double expense) {
    }
    
    private final void setupBarChart(java.util.Map<java.lang.String, java.lang.Double> dataMap) {
    }
    
    private final void pickDate(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback) {
    }
}