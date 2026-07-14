package com.group.coinquest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u000f"}, d2 = {"Lcom/group/coinquest/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "achievementDao", "Lcom/group/coinquest/AchievementDao;", "budgetDao", "Lcom/group/coinquest/BudgetDao;", "categoryDao", "Lcom/group/coinquest/CategoryDao;", "transactionDao", "Lcom/group/coinquest/TransactionDao;", "userDao", "Lcom/group/coinquest/UserDao;", "userProgressDao", "Lcom/group/coinquest/UserProgressDao;", "app_debug"})
@androidx.room.Database(entities = {com.group.coinquest.User.class, com.group.coinquest.Transaction.class, com.group.coinquest.UserProgress.class, com.group.coinquest.Achievement.class, com.group.coinquest.Category.class, com.group.coinquest.Budget.class}, version = 5)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.group.coinquest.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.group.coinquest.TransactionDao transactionDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.group.coinquest.UserProgressDao userProgressDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.group.coinquest.AchievementDao achievementDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.group.coinquest.CategoryDao categoryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.group.coinquest.BudgetDao budgetDao();
}