#!/usr/bin/env python3
"""
Scripts Package Entry Point
============================

This module allows running scripts in the scripts package using:
    uv run scripts script_name.py [args...]

Available scripts:
    - setup_env.py: Setup GitHub repository variables and secrets
    - sync_with_dev.py: Sync feature branch with development

Usage:
    uv run scripts setup_env.py [path-to-env-file] [repo-name]
    uv run scripts sync_with_dev.py
"""

import sys
import subprocess
from pathlib import Path


def main():
    """Main entry point for the scripts package."""
    if len(sys.argv) < 2:
        print_usage()
        sys.exit(1)

    script_name = sys.argv[1]
    script_args = sys.argv[2:] if len(sys.argv) > 2 else []

    # Get the directory where this __main__.py is located
    scripts_dir = Path(__file__).parent
    script_path = scripts_dir / script_name

    # Check if the script exists
    if not script_path.exists():
        print(f"❌ Error: Script '{script_name}' not found in scripts directory.")
        print()
        print_available_scripts()
        sys.exit(1)

    # Check if it's a Python file
    if not script_name.endswith('.py'):
        print(f"❌ Error: '{script_name}' is not a Python script.")
        sys.exit(1)

    try:
        # Execute the script with the provided arguments
        cmd = [sys.executable, str(script_path)] + script_args
        result = subprocess.run(cmd, check=True)
        sys.exit(result.returncode)
    except subprocess.CalledProcessError as e:
        print(f"❌ Error: Script '{script_name}' failed with exit code {e.returncode}")
        sys.exit(e.returncode)
    except KeyboardInterrupt:
        print("\n⚠️  Script execution interrupted by user.")
        sys.exit(130)
    except Exception as e:
        print(f"❌ Error executing script '{script_name}': {e}")
        sys.exit(1)


def print_usage():
    """Print usage information."""
    print("📋 SkillLink Scripts Package")
    print("============================")
    print()
    print("Usage:")
    print("    uv run scripts <script_name.py> [args...]")
    print()
    print_available_scripts()


def print_available_scripts():
    """Print available scripts in the scripts directory."""
    scripts_dir = Path(__file__).parent
    python_scripts = [f for f in scripts_dir.iterdir()
                     if f.is_file() and f.suffix == '.py' and f.name != '__init__.py' and f.name != '__main__.py']

    if python_scripts:
        print("Available scripts:")
        for script in sorted(python_scripts):
            script_description = get_script_description(script)
            print(f"    • {script.name} - {script_description}")
    else:
        print("No Python scripts found in the scripts directory.")

    print()
    print("Examples:")
    print("    uv run scripts setup_env.py")
    print("    uv run scripts setup_env.py .env")
    print("    uv run scripts setup_env.py .env owner/repo-name")
    print("    uv run scripts sync_with_dev.py")


def get_script_description(script_path: Path) -> str:
    """Extract a brief description from the script's docstring."""
    try:
        with open(script_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Look for the first docstring
        lines = content.split('\n')
        in_docstring = False
        description_lines: list[str] = []

        for line in lines:
            stripped = line.strip()
            if not in_docstring and (stripped.startswith('"""') or stripped.startswith("'''")):
                in_docstring = True
                # If the docstring starts and ends on the same line
                if stripped.count('"""') == 2 or stripped.count("'''") == 2:
                    return stripped.strip('"""').strip("'''").strip()
                continue
            elif in_docstring:
                if stripped.endswith('"""') or stripped.endswith("'''"):
                    break
                if stripped and not stripped.startswith('=') and len(stripped) > 3:
                    description_lines.append(stripped)
                    if len(description_lines) >= 2:  # Get first meaningful line
                        break

        if description_lines:
            return description_lines[0]

    except Exception:
        pass

    return "No description available"


if __name__ == "__main__":
    main()