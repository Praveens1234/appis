# Appis - Autonomous Android App Builder

Appis is a revolutionary "Glass Box" AI development environment that builds real, functional Android applications directly from your phone. Unlike "black box" code generators, Appis exposes every step of the software engineering process—planning, coding, auditing, committing, building, and healing—through a stunning transparent UI.

## 🚀 Key Features

Appis is built on a 50+ module architecture designed for robustness, autonomy, and transparency.

### 🧠 Autonomous AI Agents
1.  **AgentPlanner (Architecture):** break down user requests into technical implementation plans.
2.  **AgentCoder (Implementation):** Writes Clean Architecture (MVVM) Kotlin code.
3.  **AgentAuditor (Security/Style):** statically analyzes code for bugs and security risks.
4.  **AgentHealer (Self-Correction):** Monitors build logs and autonomously fixes errors.
5.  **AgentReviewer (QA):** Ensures all changes meet strict quality gates.

### ⚙️ The Pipeline (RealOrchestrator)
The Appis pipeline is a directed acyclic graph (DAG) of stages:
*   **Plan Stage:** Negotiates requirements with the LLM.
*   **Code Stage:** Generates source code, resources, and manifests.
*   **Audit Stage:** Performs local verification.
*   **Sync Stage:** Commits and Pushes to GitHub (Atomic operations).
*   **Build Stage:** Triggers and monitors GitHub Actions workflows.
*   **Diagnosis Stage:** Analyzes failure logs if a build fails.
*   **Heal Stage:** Applies patches to fix diagnosed issues.

### 🎨 Glass UI & Experience
*   **Transparent Design:** Beautiful "Glassmorphism" UI with Neon accents.
*   **Live Pipeline Graph:** Watch the AI move through stages in real-time.
*   **Streaming Chat:** Typewriter-style communication with the System.
*   **Code Viewer:** Inspect the generated code with syntax highlighting.
*   **Dashboard:** Manage multiple projects and API keys.

### 🛠️ Technical Stack
*   **Architecture:** MVVM + Clean Architecture.
*   **Language:** Kotlin.
*   **DI:** Hilt (Dagger).
*   **Concurrency:** Kotlin Coroutines & Flow.
*   **Persistence:** Room Database + EncryptedSharedPreferences.
*   **Networking:** Retrofit (Nvidia API, GitHub REST API).
*   **Git Integration:** Custom `RepoManager` (No native git libraries required).

---

## 📱 User Guide

### 1. Initial Setup
1.  Open Appis.
2.  Tap the **Settings** icon (top right or menu).
3.  Enter your **Nvidia API Key** (for LLM intelligence).
4.  Enter your **GitHub Personal Access Token** (Classic, with `repo` and `workflow` scopes).
5.  Enter your **GitHub Username**.
6.  Tap **Save**.

### 2. Creating a Project
1.  On the Dashboard, tap **Create New Project**.
2.  **Title:** Give your app a name (e.g., "CryptoTracker").
3.  **Description:** Describe what you want. Be specific!
    *   *Example:* "A crypto price tracker using the CoinGecko API. Show a list of top 10 coins with prices in USD. Click a coin to see details."
4.  Tap **Start Building**.

### 3. Monitoring the Build
1.  You will be taken to the **Chat Screen**.
2.  Watch the **Pipeline Graph** at the top. It highlights the active stage.
3.  The chat will update you on progress ("Planning...", "Writing code...", "Pushing to GitHub...").
4.  Once the **Code Stage** is done, a **View Code** button appears. You can tap it to inspect the generated files.

### 4. Build & Download
1.  The **Build Stage** will trigger a GitHub Action on your repository.
2.  Appis polls the status.
3.  If successful, the APK link will be provided in the chat.
4.  If it fails, the **Heal Stage** kicks in automatically to fix the bug and retry.

---

## 🔧 Technical Documentation (For Developers)

### Project Structure
```
com.appis.android
├── data/
│   ├── api/          # Retrofit services (Nvidia, GitHub)
│   ├── database/     # Room entities and DAOs
│   ├── repository/   # RepoManager (Git logic)
│   └── prefs/        # SettingsManager
├── domain/
│   ├── model/        # Data classes (Project, ChatMessage)
│   └── orchestrator/ # The Brain
│       ├── stages/   # Individual pipeline steps (Plan, Code, Build...)
│       ├── SaasOrchestrator.kt # Event loop and Stage management
│       └── RealOrchestrator.kt # Public API for the UI
├── di/               # Hilt Modules
└── ui/               # Jetpack Compose Screens
```

### Key Classes
*   **`SaasOrchestrator`:** The heart of the system. It manages the `EventBus` and executes `PipelineStage` implementations sequentially or conditionally.
*   **`RepoManager`:** Handles Git operations using the GitHub Tree/Blob API. It creates commits manually by constructing Git trees, allowing for library-free git operations on Android.
*   **`NvidiaClient`:** Interfaces with high-performance LLMs to generate code and plans.

### Building Appis
To build the Appis project itself:
1.  Clone the repository.
2.  Open in Android Studio (or use command line).
3.  Run `./gradlew assembleDebug`.
4.  Deploy to a device/emulator.

*Note: You need a `local.properties` file with your SDK path if building locally.*

---

## 📋 Features Checklist (50+ Points)

**Core Engine**
1. [x] RealOrchestrator Interface
2. [x] SaasOrchestrator Implementation
3. [x] Asynchronous EventBus
4. [x] Global Exception Handling
5. [x] Coroutine Scope Management
6. [x] Room Database Integration
7. [x] Encrypted Storage
8. [x] Singleton Pattern Enforcement

**Agents & AI**
9. [x] Nvidia API Client
10. [x] Context Management
11. [x] Prompt Engineering (System Prompts)
12. [x] AgentPlanner Logic
13. [x] AgentCoder Logic
14. [x] AgentAuditor Logic
15. [x] AgentReviewer Logic
16. [x] AgentHealer Logic
17. [x] Streaming Response Support
18. [x] Token Management

**Pipeline Stages**
19. [x] Abstract PipelineStage
20. [x] PlanStage (Requirements)
21. [x] CodeStage (Generation)
22. [x] AuditStage (Verification)
23. [x] SyncStage (GitHub Push)
24. [x] BuildStage (CI/CD Trigger)
25. [x] DiagnosisStage (Log Analysis)
26. [x] HealStage (Patching)

**Git & Version Control**
27. [x] GitHub REST Client
28. [x] Blob Creation
29. [x] Tree Construction
30. [x] Commit Creation
31. [x] Reference Updates (Push)
32. [x] Workflow Dispatch
33. [x] Run Status Polling
34. [x] Log Retrieval

**User Interface**
35. [x] Jetpack Compose Architecture
36. [x] Glassmorphism Theme
37. [x] Dark/Neon Color Palette
38. [x] Dashboard Screen
39. [x] Create Project Flow
40. [x] Chat Interface
41. [x] Typewriter Text Effect
42. [x] Pipeline Visualization (Graph)
43. [x] Code Syntax Highlighting
44. [x] Settings Screen
45. [x] Live Status Badges
46. [x] Navigation Graph
47. [x] Error Dialogs/Snackbars

**Security & Stability**
48. [x] Secure Key Storage
49. [x] Dependency Injection (Hilt)
50. [x] Deadlock Prevention (Buffered Channels)
