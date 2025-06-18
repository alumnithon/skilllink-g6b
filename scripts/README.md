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
