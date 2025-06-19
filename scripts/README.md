# SkillLink Setup Scripts

## setup_env.py

A cross-platform Python script that automatically configures GitHub repository variables and secrets
for SkillLink's CI/CD workflows.

### What it does

- **Parses `.env` files** with Variables and Secrets sections
- **Sets GitHub Variables** (non-sensitive config like database names, ports)
- **Sets GitHub Secrets** (sensitive data like passwords, API keys)
- **Cross-platform compatible** (Windows, macOS, Linux)
- **Repository-aware** with permission checking

### Requirements

- **Python 3.13+** or **uv** (recommended)
- **GitHub CLI** (`gh`) installed and authenticated
- **Organization admin permissions** - Only organization admins can manage repository variables and
  secrets
- **Repository access** with admin/write permissions

### Quick Start

1. **Copy environment template:**

   ```bash
   cp .env.example .env
   ```

2. **Edit `.env` with your values:**

   ```bash
   # Variables (non-sensitive)
   MYSQL_DATABASE=skilllink_db
   MYSQL_PORT=3306

   # Secrets (sensitive)
   MYSQL_ROOT_PASSWORD=your_secure_password
   JWT_SECRET=your_jwt_secret
   ```

3. **Run the setup:**

   ```bash
   # With uv (recommended)
   uv run setup_env.py .env

   # With Python
   python3 setup_env.py .env

   # For specific repository
   uv run setup_env.py .env owner/repository-name
   ```

### Platform-Specific Instructions

#### macOS/Linux

```bash
# Install GitHub CLI
brew install gh  # macOS
# or
sudo apt install gh  # Ubuntu/Debian

# Authenticate
gh auth login

# Run script
uv run setup_env.py .env
```

#### Windows

```powershell
# Install GitHub CLI
winget install GitHub.cli

# Authenticate
gh auth login

# Run script
uv run setup_env.py .env
```

### Verification

After running the script, verify the setup:

```bash
gh variable list
gh secret list
```

### Security Notes

- ✅ `.env` files are git-ignored automatically
- ✅ Secrets are never displayed in output
- ✅ Permission validation before making changes
- ⚠️ Never commit actual `.env` files to version control

## sync_with_dev.py

A cross-platform Python script for developers to manually sync their feature branch with the
development branch.

### What it does

- **Checks branch compatibility** - Ensures you're on a `development/feat/*` branch
- **Validates working directory** - Ensures no uncommitted changes
- **Fetches latest changes** from remote repository
- **Attempts automatic merge** with development branch
- **Handles merge conflicts** with clear instructions
- **Pushes successful merges** automatically
- **Cross-platform compatible** (Windows, macOS, Linux)

### When to use

- When GitHub Actions reports merge conflicts in your feature branch
- For manual syncing before starting new work
- When you want to ensure your branch is up-to-date

### Requirements

- **Python 3.10+** (widely available)
- **Git** installed and configured
- **Currently on a development/feat/\* branch**
- **Clean working directory** (no uncommitted changes)

### Usage

```bash
# From project root
python scripts/sync_with_dev.py

# Alternative ways to run
python3 scripts/sync_with_dev.py
py scripts/sync_with_dev.py        # Windows
```

### Example Output

```
🔄 Syncing feature branch with development...

📍 Current branch: development/feat/user-authentication
📍 Target branch: development

✅ Feature branch detected: development/feat/user-authentication
🔄 Fetching latest changes from remote...
🔀 Attempting to merge development into development/feat/user-authentication...

✅ Successfully synced development/feat/user-authentication with development
🚀 Pushing changes to remote...

🎉 Sync complete!
```

### Conflict Resolution

If merge conflicts occur:

```
❌ Merge conflicts detected!
📋 Next steps:
  1. Resolve conflicts in your IDE
  2. Stage resolved files: git add .
  3. Complete the merge: git commit
  4. Push changes: git push origin development/feat/your-branch

💡 Tip: Use 'git status' to see conflicted files
💡 Tip: Use 'git merge --abort' to cancel if needed
```

### Integration with GitHub Actions

This script works in tandem with the GitHub Actions workflow
(`.github/workflows/sync-feature-branches.yml`):

1. **GitHub Actions** automatically syncs branches when development is updated
2. **If conflicts occur**, Actions will notify you to run this script manually
3. **This script** handles the manual conflict resolution process
