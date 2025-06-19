# /// script
# requires-python = ">=3.13"
# dependencies = []
# ///

"""
GitHub Repository Variables and Secrets Setup Script
====================================================
This script reads from a .env file and sets up all the variables and secrets
needed for the SkillLink project's GitHub Actions workflows.

Usage: uv run setup_env.py [path-to-env-file] [repo-name]
Default: uv run setup_env.py .env
Example: uv run setup_env.py .env owner/repository-name
"""

import sys
import subprocess
import re
from pathlib import Path


def print_banner() -> None:
    """Print the script banner."""
    print("🚀 Setting up GitHub Repository Variables and Secrets for SkillLink")
    print("==================================================================")


def print_example_env() -> None:
    """Print an example .env file structure."""
    print("Example .env file structure:")
    print("# Variables")
    print("MYSQL_DATABASE=skilllink_db")
    print("MYSQL_PORT=3306")
    print("# Secrets")
    print("MYSQL_ROOT_PASSWORD=your_secure_password")
    print("JWT_SECRET=your_jwt_secret")


def check_env_file(env_file_path: Path) -> bool:
    """Check if the .env file exists."""
    if not env_file_path.exists():
        print(f"❌ Error: .env file not found at '{env_file_path}'")
        print("Please create a .env file or specify the correct path.")
        print()
        print_example_env()
        return False
    return True


def run_gh_command(command: list[str]) -> bool:
    """
    Run a GitHub CLI command in a cross-platform way.

    Args:
        command: List of command arguments

    Returns:
        bool: True if command succeeded, False otherwise
    """
    try:
        subprocess.run(
            command,
            capture_output=True,
            text=True,
            check=True
        )
        return True
    except subprocess.CalledProcessError as e:
        print(f"❌ Error running command: {' '.join(command)}")
        print(f"Error: {e.stderr}")
        return False
    except FileNotFoundError:
        print("❌ Error: GitHub CLI (gh) not found. Please install it first.")
        print("Visit: https://github.com/cli/cli#installation")
        return False


def check_gh_cli_and_set_repo(repo_name: str | None = None) -> bool:
    """
    Check if GitHub CLI is available and optionally set the default repository.

    Args:
        repo_name: Optional repository name in format 'owner/repo'

    Returns:
        bool: True if GitHub CLI is available and repo is set (if provided), False otherwise
    """
    # First check if gh CLI is available
    try:
        subprocess.run(
            ['gh', '--version'],
            capture_output=True,
            text=True,
            check=True
        )
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌ Error: GitHub CLI (gh) not found. Please install it first.")
        print("Visit: https://github.com/cli/cli#installation")
        return False

    # If repo name is provided, try to set it as default
    if repo_name:
        print(f"🔧 Setting default repository to: {repo_name}")
        try:
            subprocess.run(
                ['gh', 'repo', 'set-default', repo_name],
                capture_output=True,
                text=True,
                check=True
            )
            print(f"✅ Repository set successfully: {repo_name}")
            return True
        except subprocess.CalledProcessError as e:
            print(f"❌ Error setting repository '{repo_name}': {e.stderr.strip()}")
            print("Possible issues:")
            print("  - Repository doesn't exist or you don't have access")
            print("  - Invalid repository format (should be 'owner/repo')")
            print("  - You're not authenticated with GitHub CLI")
            print("Try: gh auth login")
            return False

    return True


def check_repo_permissions(repo_name: str | None = None) -> bool:
    """
    Check if the user has permissions to manage repository variables and secrets.

    Args:
        repo_name: Optional repository name in format 'owner/repo'

    Returns:
        bool: True if user has permissions, False otherwise
    """
    if not repo_name:
        print("⚠️  No repository specified. Commands will use the current directory's repository.")
        return True

    print(f"🔍 Checking permissions for repository: {repo_name}")

    # Test permissions by trying to list variables (least invasive check)
    try:
        subprocess.run(
            ['gh', 'variable', 'list', '--repo', repo_name],
            capture_output=True,
            text=True,
            check=True
        )
        print("✅ Repository access confirmed - you have permissions to manage variables and secrets")
        return True
    except subprocess.CalledProcessError as e:
        print(f"❌ Permission check failed: {e.stderr.strip()}")
        print("Possible issues:")
        print("  - You don't have admin/write access to the repository")
        print("  - Repository is private and you don't have access")
        print("  - You need to be authenticated with appropriate permissions")
        print("Try: gh auth login --scopes repo")
        return False


def parse_env_file(env_file_path: Path) -> tuple[dict[str, str], dict[str, str]]:
    """
    Parse the .env file and separate variables from secrets.

    Args:
        env_file_path: Path to the .env file

    Returns:
        Tuple of (variables_dict, secrets_dict)
    """
    variables: dict[str, str] = {}
    secrets: dict[str, str] = {}
    current_section = ""

    with open(env_file_path, 'r', encoding='utf-8') as file:
        for line in file:
            line = line.strip()

            # Skip empty lines
            if not line:
                continue

            # Check for section headers
            if re.match(r'^#\s*Variables\s*$', line, re.IGNORECASE):
                current_section = "variables"
                continue
            elif re.match(r'^#\s*Secrets\s*$', line, re.IGNORECASE):
                current_section = "secrets"
                continue

            # Skip other comments
            if line.startswith('#'):
                continue

            # Parse key=value pairs
            match = re.match(r'^([^=]+)=(.*)$', line)
            if match:
                key = match.group(1).strip()
                value = match.group(2).strip()

                # Remove quotes from value if present
                value = re.sub(r'^["\']|["\']$', '', value)

                if current_section == "variables":
                    variables[key] = value
                elif current_section == "secrets":
                    secrets[key] = value

    return variables, secrets


def set_github_variables(variables: dict[str, str], repo_name: str | None = None) -> bool:
    """Set GitHub repository variables."""
    if not variables:
        return True

    print("📝 Setting up Repository Variables (non-sensitive configuration)...")
    success = True

    for var_name, var_value in variables.items():
        print(f"Setting {var_name}...")
        command = ['gh', 'variable', 'set', var_name, '--body', var_value]
        if repo_name:
            command.extend(['--repo', repo_name])

        if not run_gh_command(command):
            success = False

    print()
    return success


def set_github_secrets(secrets: dict[str, str], repo_name: str | None = None) -> bool:
    """Set GitHub repository secrets."""
    if not secrets:
        return True

    print("🔐 Setting up Repository Secrets (sensitive data)...")
    success = True

    for secret_name, secret_value in secrets.items():
        print(f"Setting {secret_name}...")
        command = ['gh', 'secret', 'set', secret_name, '--body', secret_value]
        if repo_name:
            command.extend(['--repo', repo_name])

        if not run_gh_command(command):
            success = False

    print()
    return success


def print_summary(variables: dict[str, str], secrets: dict[str, str]) -> None:
    """Print a summary of configured variables and secrets."""
    print("✅ Setup completed!")
    print()
    print("📋 Summary of configured variables and secrets:")

    # Display variables summary
    if variables:
        print("Variables:")
        for var_name, var_value in variables.items():
            print(f"  - {var_name}: {var_value}")
        print()

    # Display secrets summary
    if secrets:
        print("Secrets:")
        for secret_name in secrets.keys():
            print(f"  - {secret_name}: [HIDDEN]")
        print()

    print("🔍 You can verify the setup with:")
    print("  gh variable list")
    print("  gh secret list")
    print()
    print("🌟 Your GitHub Actions workflows are now ready to use these variables!")


def main() -> None:
    """Main function to orchestrate the setup process."""
    print_banner()

    # Get .env file path and optional repo name from command line arguments
    env_file_path = Path(sys.argv[1] if len(sys.argv) > 1 else '.env')
    repo_name = sys.argv[2] if len(sys.argv) > 2 else None

    # Check if .env file exists
    if not check_env_file(env_file_path):
        sys.exit(1)

    # Check GitHub CLI and set repository if provided
    if not check_gh_cli_and_set_repo(repo_name):
        sys.exit(1)

    # Check repository permissions
    if not check_repo_permissions(repo_name):
        sys.exit(1)

    print(f"📄 Reading configuration from: {env_file_path}")
    if repo_name:
        print(f"🎯 Target repository: {repo_name}")
    print()

    try:
        # Parse .env file
        variables, secrets = parse_env_file(env_file_path)

        # Set up variables and secrets
        variables_success = set_github_variables(variables, repo_name)
        secrets_success = set_github_secrets(secrets, repo_name)

        if not (variables_success and secrets_success):
            print("❌ Some operations failed. Please check the errors above.")
            sys.exit(1)

        # Print summary
        print_summary(variables, secrets)

    except Exception as e:
        print(f"❌ Error: {e}")
        sys.exit(1)


if __name__ == "__main__":
    main()
