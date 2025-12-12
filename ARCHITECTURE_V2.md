# Appis V5 Architecture & 100-Module Blueprint

This document defines the comprehensive 100-module plan to achieve the "Full Stack" autonomous app builder as requested.

## Phase 1: Foundation (Modules 1-10)
1.  **Core Application Class**: Global state, DI container initialization.
2.  **AppContainer (DI)**: Manual dependency injection for testability.
3.  **EncryptedPrefs**: Secure storage for Nvidia/GitHub keys.
4.  **NetworkModule**: Retrofit builders with timeouts and logging.
5.  **SafeApiCall**: Wrapper for catching HTTP/Network errors safely.
6.  **JsonSanitizer**: Robust regex-based cleaner for LLM JSON outputs.
7.  **LogUtils**: Centralized logging for the chat interface.
8.  **AppTheme**: Material 3 theme definition with custom colors.
9.  **GlassEffects**: Modifiers for blur and transparency.
10. **NeonStyles**: Custom shadow and border modifiers.

## Phase 2: Agents (Modules 11-30)
11. **PromptManager**: Loader for `assets/prompts.json`.
12. **AgentPlanner (Prompt)**: "You are a Senior Architect..."
13. **AgentPlanner (Logic)**: Validates JSON output against schema.
14. **AgentCoder (Prompt)**: "You are a Principal Kotlin Engineer..."
15. **AgentCoder (Context)**: Injects file summaries into prompt.
16. **AgentCoder (Logic)**: Writes files to disk, handles missing directories.
17. **AgentAuditor (Prompt)**: "You are a Code Reviewer..."
18. **AgentAuditor (Logic)**: static analysis (lint) or LLM-based check.
19. **AgentCorrector (Prompt)**: "You are a Bug Fixer..."
20. **AgentCorrector (Diffing)**: Logic to apply patch vs full rewrite.
21. **TokenManager**: Estimates token usage to prevent overflow.
22. **ContextGraph**: Maps file dependencies (A imports B).
23. **RetryPolicy**: Exponential backoff for failed LLM calls.
24. **StreamingClient**: SSE reader for typewriter effects (Future).
25. **ModelSwitcher**: UI/Logic to swap models (Qwen, DeepSeek).
26. **OutputValidator**: Ensures generated code has matching braces.
27. **PackageResolver**: Fixes incorrect `package x.y.z` declarations.
28. **ImportFixer**: Auto-adds missing imports if obvious.
29. **HallucinationGuard**: Checks against `symbols.json`.
30. **AgentLogger**: Feeds detailed internal thoughts to UI.

## Phase 3: Git & FMS (Modules 31-50)
31. **FmsService**: Local file system abstraction.
32. **ZipExporter**: Recursive zipper for Codebase/KB.
33. **FileTreeBuilder**: Helper to visualize structure.
34. **GitHubApi**: Retrofit interface for REST v3.
35. **RepoManager**: `createRepo`, `checkRepoExists`.
36. **GitCommitBuilder**: Creates Tree objects for atomic commits.
37. **GitPusher**: Uses `git/refs` to update `main`.
38. **WorkflowInjector**: Pushes `android_build.yml`.
39. **BuildPoller**: Loops checking `actions/runs`.
40. **LogFetcher**: Downloads raw text of build logs.
41. **LogParser**: Regex extraction of compile errors.
42. **ArtifactDownloader**: Fetches produced APKs.
43. **DiffViewer**: (Optional) Shows changes.
44. **CacheManager**: Cleans up old project files.
45. **KbRepository**: Stores structured knowledge (JSON).
46. **KbExporter**: Exports KB to ZIP.
47. **SymbolTable**: Tracks defined classes/functions.
48. **DependencyGraph**: Tracks gradle dependencies.
49. **VersionControl**: Simple local undo/redo (optional).
50. **AutoSave**: Persists state across app restarts.

## Phase 4: Orchestration (Modules 51-70)
51. **PipelineStep**: Enum (PLAN, CODE, AUDIT, SYNC, BUILD).
52. **PipelineState**: Data class for UI observation.
53. **SaasOrchestrator**: The central "Brain".
54. **EventBus**: async messaging between components.
55. **PlanStage**: Execute Agent A.
56. **CodeStage**: Execute Agent B (parallel or serial).
57. **AuditStage**: Execute Agent C.
58. **SyncStage**: Execute Git Ops.
59. **BuildStage**: Polling loop.
60. **HealStage**: Logic to trigger Agent D.
61. **UserIntervention**: Logic to pause for user input.
62. **ChatProcessor**: Routes user text to Agents.
63. **FeedbackLoop**: Re-injects user comments into context.
64. **ErrorHandler**: Global try/catch block for pipeline.
65. **RecoveryMechanism**: Resume from last successful step.
66. **NotificationManager**: System notifications (Android).
67. **BackgroundService**: `PipelineService` (Foreground).
68. **WakeLock**: Keep CPU running during code gen.
69. **BatteryOptimizer**: Check/Request ignore optimizations.
70. **SelfUpdate**: (Theoretical) App updates itself.

## Phase 5: UI/UX (Modules 71-90)
71. **NavHost**: Compose Navigation setup.
72. **DashboardScreen**: Project list and status.
73. **CreateProjectScreen**: Input form with validation.
74. **ChatScreen**: The main interface (LazyColumn).
75. **ChatBubble**: Custom composable with glass style.
76. **TypingIndicator**: Animated dots.
77. **CodebaseScreen**: Split view (Tree + Code).
78. **SyntaxHighlighter**: Basic color transformation.
79. **BuildStatusScreen**: List of workflow runs.
80. **BuildLogViewer**: Raw text viewer for logs.
81. **SettingsScreen**: API keys and toggles.
82. **KnowledgeBaseScreen**: JSON tree viewer.
83. **Dialogs**: Error alerts, Confirmation modals.
84. **Animations**: Transitions between screens.
85. **ThemeToggle**: Dark/Light (forced Dark for futuristic).
86. **GlassCard**: Reusable surface component.
87. **NeonButton**: Reusable action component.
88. **StatusBadge**: Reusable pill component.
89. **FileIcon**: Dynamic icons based on extension.
90. **LoadingState**: Shimmer effects.

## Phase 6: Delivery (Modules 91-100)
91. **UnitTests**: Verify strict JSON parsing.
92. **IntegrationTests**: Verify Git API calls (mocked).
93. **PerformanceTuning**: Optimize Recomposition.
94. **MemoryLeakCheck**: LeakCanary (optional).
95. **SecurityAudit**: Check key storage.
96. **AssetPrep**: Icons, logos, prompts.
97. **GradleConfig**: Release build types, shrinking.
98. **Documentation**: README and Help.
99. **FinalBuild**: Assemble APK.
100. **Submission**: Upload to hosting.
