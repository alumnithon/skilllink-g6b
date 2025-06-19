#!/usr/bin/env python3
"""
Sync Feature Branch with Development
====================================

Script for developers to manually sync their feature branch with development.
Use this when GitHub Actions reports merge conflicts or for manual syncing.

Cross-platform compatible script that works on Windows, macOS, and Linux.

Usage:
    uv run scripts example.py

Requirements:
    - uv installed
        - Install on Windows: `powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"`
        - Install on Linux/macOS: `curl -LsSf https://astral.sh/uv/install.sh | sh`
    - Git installed and configured
    - Currently on a development/feat/* branch
"""

import subprocess
import sys


def run_git_command(command, capture_output=True, check=True):
    """Run a git command and return the result."""
    try:
        result = subprocess.run(
            command,
            shell=True,
            capture_output=capture_output,
            text=True,
            check=check
        )
        return result
    except subprocess.CalledProcessError as e:
        if not capture_output:
            return e
        raise e


def print_status(message, emoji="📍"):
    """Print a status message with emoji."""
    print(f"{emoji} {message}")


def print_error(message):
    """Print an error message."""
    print(f"❌ {message}")


def print_success(message):
    """Print a success message."""
    print(f"✅ {message}")


def print_warning(message):
    """Print a warning message."""
    print(f"⚠️  {message}")


def main():
    """Main function to sync feature branch with development."""

    # Configuration
    DEV_BRANCH = "development"

    print("🔄 Syncing feature branch with development...")
    print()

    # Check if we're in a git repository
    try:
        run_git_command("git rev-parse --git-dir")
    except subprocess.CalledProcessError:
        print_error("Not in a Git repository!")
        sys.exit(1)

    # Get current branch
    try:
        result = run_git_command("git branch --show-current")
        current_branch = result.stdout.strip()
    except subprocess.CalledProcessError:
        print_error("Could not determine current branch!")
        sys.exit(1)

    print_status(f"Current branch: {current_branch}")
    print_status(f"Target branch: {DEV_BRANCH}")
    print()

    # Check if current branch is a development feature branch
    if not current_branch.startswith("development/feat/"):
        print_error("Not on a development feature branch.")
        print_status(f"Current branch: {current_branch}")
        print_status("Expected pattern: development/feat/*")
        print()
        print("Please switch to a development/feat/ branch first:")
        print("  git checkout development/feat/your-feature-name")
        sys.exit(1)

    print_success(f"Feature branch detected: {current_branch}")

    # Check if working directory is clean
    try:
        result = run_git_command("git status --porcelain")
        if result.stdout.strip():
            print_warning("Working directory is not clean. Please commit or stash changes first:")
            run_git_command("git status --short", capture_output=False)
            sys.exit(1)
    except subprocess.CalledProcessError:
        print_error("Could not check working directory status!")
        sys.exit(1)

    # Fetch latest changes
    print("🔄 Fetching latest changes from remote...")
    try:
        run_git_command("git fetch origin", capture_output=False)
    except subprocess.CalledProcessError:
        print_error("Failed to fetch from remote!")
        sys.exit(1)

    # Attempt to merge development
    print(f"🔀 Attempting to merge {DEV_BRANCH} into {current_branch}...")
    print()

    merge_result = run_git_command(f"git merge origin/{DEV_BRANCH} --no-edit",
                                 capture_output=False, check=False)

    if merge_result.returncode == 0:
        print()
        print_success(f"Successfully synced {current_branch} with {DEV_BRANCH}")

        # Push changes
        print("🚀 Pushing changes to remote...")
        try:
            run_git_command(f"git push origin {current_branch}", capture_output=False)
            print()
            print("🎉 Sync complete!")
        except subprocess.CalledProcessError:
            print_error("Failed to push changes to remote!")
            print("Please push manually: git push origin " + current_branch)
            sys.exit(1)
    else:
        print()
        print_error("Merge conflicts detected!")
        print("📋 Next steps:")
        print("  1. Resolve conflicts in your IDE")
        print("  2. Stage resolved files: git add .")
        print("  3. Complete the merge: git commit")
        print("  4. Push changes: git push origin " + current_branch)
        print()
        print("💡 Tip: Use 'git status' to see conflicted files")
        print("💡 Tip: Use 'git merge --abort' to cancel if needed")
        sys.exit(1)


if __name__ == "__main__":
    main()
