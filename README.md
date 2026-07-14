# CoinQuest — Personal Budget Tracker

**OPSC6311 | Final POE Submission**

| Member | Student Number |
|---|---|
| Khonani Mutobvu | ST10439622 |
| Ofentse Nyiko Mashigoane | ST10456959 |
| Charity Taulene | ST10438951 |
| Khumbelo Tshikororo | ST10441369 |
| Vukosi Sono | ST10453078 |

---

## Build Instructions

1. Open the project in **Android Studio Hedgehog (2023.1.1)** or later.
2. Allow Gradle to sync (requires internet to download MPAndroidChart via JitPack).
3. Connect an Android device running **Android 8.0 (API 26)** or higher.
4. Click **Run ▶** or build the APK via **Build → Build APK(s)**.

> The app requires a **physical Android device** — not an emulator — for camera/receipt features.

---

## App Overview

CoinQuest is a gamified personal budget tracker. Users log expenses, set spending goals, earn XP and badges, and view visual analytics of their spending habits.

---

## Required Features (Final POE)

### 1. Spending Graph with Min/Max Goal Lines
**Location:** Analytics screen (accessible from Dashboard → "Analytics" button)

The Analytics screen displays:
- A **bar chart** showing total spending per category for a user-selectable date range
- **Dashed horizontal lines** on the bar chart indicating the user's minimum and maximum monthly budget goals
- A **donut/pie chart** showing the spending proportion per category
- Both charts update when the date range is changed via the date pickers

**How to test:** Set budget goals in Budget Settings, log a few expenses in different categories, then open Analytics and tap Apply.

### 2. Visual Goal Progress Display
**Location:** Dashboard (category progress bars) + Achievements screen

The Dashboard displays a **colour-coded progress bar for each category**:
- 🟢 Green — under 75% of monthly limit
- 🟡 Yellow — 75–99% of monthly limit  
- 🔴 Red — 100% or over limit

### 3. Gamification — XP & Badges
**Location:** Achievements screen (accessible from Dashboard → tap the XP/Badge card)

Four badges with XP rewards:
| Badge | Condition | XP |
|---|---|---|
| 👟 First Step | Log your first expense | +10 XP |
| 🔥 Streak Master | Log expenses 7 days in a row | +50 XP |
| 🛡️ Frugal Hero | Stay within max budget for the month | +100 XP |
| 🏆 Savings Champion | Stay below minimum goal for the month | +75 XP |

XP level = XP ÷ 1000 + 1. Progress to next level shown as a progress bar.

---

## Custom Features (Own Features — 2 Required)

### Custom Feature 1: Receipt Photo Attachment
When adding an expense, users can tap **"Capture Photo"** to take a photo of a receipt using the device camera. The photo is stored on the device and linked to the expense record in the local Room database. This feature addresses a gap identified in all three researched apps (YNAB, Goodbudget, PocketGuard) — none offer receipt attachment on Android.

**How to test:** Open Add Expense → tap "Capture Photo" → grant camera permission → take photo → save expense. The receipt preview appears on screen before saving.

### Custom Feature 2: Safe to Spend Today Calculator
The Dashboard prominently displays a **"Safe to Spend Today"** figure, automatically calculated as:

> `(Max Monthly Budget − Total Spent This Month) ÷ Days Remaining in Month`

This gives users an instant daily spending allowance without manual calculation, directly inspired by PocketGuard's "In My Pocket" feature as identified in the research phase. The figure updates every time the user returns to the Dashboard.

**How to test:** Set a budget in Budget Settings → log some expenses → return to Dashboard to see the updated daily figure.

---

## Database Schema

| Table | Key Fields |
|---|---|
| `users_table` | userId, username, email, passwordHash, xp, currentStreak, lastLoginDate, earnedBadges |
| `categories_table` | categoryId, userId, name, monthlyMinGoal, monthlyMaxGoal |
| `expenses_table` | expenseId, userId, categoryId, categoryName, amount, date, startTime, endTime, description, receiptPhotoPath |
| `budget_goals_table` | goalId, userId, minMonthlyGoal, maxMonthlyGoal |

---

## Navigation

```
LOGIN / REGISTER
       ↓
  QUEST DASHBOARD ──→ ANALYTICS
       │                
       ├──→ ADD EXPENSE  
       ├──→ TRANSACTIONS  
       ├──→ BUDGET SETTINGS  
       ├──→ MANAGE CATEGORIES  
       └──→ ACHIEVEMENTS  
```

---

## Libraries Used

- **Room** (androidx.room 2.6.1) — local SQLite database
- **MPAndroidChart** (PhilJay v3.1.0) — bar and pie charts
- **Kotlin Coroutines** — background database operations
- **AndroidX ViewBinding** — type-safe view access
- **Material Components** — UI components

---

## Known Limitations

- Passwords are stored as plain text (hashing was planned but not implemented in the final build; this is noted for future improvement).
- Streak counter increments on login, not on expense logging — a deliberate design choice to reward daily engagement.
