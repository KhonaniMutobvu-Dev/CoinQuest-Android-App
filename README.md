# CoinQuest 💰
### A Gamified Personal Budget Tracker for Android

CoinQuest is an Android budgeting application built to make personal financial management engaging and rewarding. Instead of treating budgeting as a chore, CoinQuest turns it into a quest — rewarding users with XP, badges, and streaks for staying on top of their finances.

Built as a group academic project for OPSC6311 at Rosebank College (IIE), 2026.

---

## 📱 Features

- **User Authentication** — Secure login with username and password
- **Custom Budget Categories** — Create, edit, and delete your own spending categories with individual monthly limits
- **Expense Tracking** — Log expenses with date, start/end time, description, category, and optional photo receipt
- **Monthly Spending Goals** — Set minimum and maximum monthly spending targets
- **Filterable Expense List** — View all expenses for any user-selected time period, with photo access directly from the list
- **Category Spending Reports** — View total amount spent per category over any selected period
- **Gamification Engine** — Earn XP and badges for financial milestones (streaks, staying within budget, hitting savings goals)
- **Quest Dashboard** — Home screen displays active budget missions, XP level, badge, and streak counter
- **Offline-First Architecture** — All data stored locally using Room Database (SQLite) — no internet required

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| Platform | Android (Android Studio) |
| Database | Room DB (SQLite) |
| UI | XML Layouts, Material Design |
| Version Control | Git & GitHub |
| CI/CD | GitHub Actions |
| Testing | JUnit, MsTest |

---

## 👥 Team

| Student Number | Name | Role |
|---|---|---|
| ST10439622 | Khonani Mutobvu | Project Manager |
| ST10456959 | Ofentse Nyiko Mashigoane | Senior Developer |
| ST10438951 | Charity Taulene | Developer |
| ST10441369 | Khumbelo Tshikororo | UX/UI Designer |
| ST10453078 | Vukosi Sono | Backend Developer |

---

## 🎯 Project Management

This project was managed by Khonani Mutobvu, who was responsible for:
- Coordinating all five team members across design, development, and backend
- Managing task distribution and tracking progress across all development phases
- Running sprint planning and ensuring delivery against academic deadlines
- Resolving blockers and keeping the team aligned throughout the project lifecycle

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable version)
- Android SDK (minimum API level 24)
- JDK 11 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/KhonaniMutobvu-Dev/CoinQuest-Android-App.git
```

2. Open the project in Android Studio

3. Let Gradle sync and resolve dependencies

4. Run the app on an emulator or physical Android device

### Build APK
```bash
./gradlew assembleDebug
```

---

## 🧪 Testing

Run automated tests with:
```bash
./gradlew test
```

GitHub Actions is configured to automatically build and run tests on every push to main.

---


## 📄 Academic Context

- **Module:** OPSC6311 — Open Source Coding
- **Institution:** Rosebank College (The Independent Institute of Education)
- **Year:** 2026

---

## 📚 Research Foundation

CoinQuest's feature set was informed by a critical analysis of YNAB, Goodbudget, and PocketGuard. Key design decisions include animated category progress bars, a Safe to Spend dashboard metric, and a full gamification layer with XP, badges, and streaks.

---

*Built with Kotlin · Powered by Room DB · Managed with GitHub*
