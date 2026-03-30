import re
from pathlib import Path
root = Path('.')
method_pattern = re.compile(r'^(\s*(public|protected|private)\s+(static\s+)?[\w<>,\[\]\s]+\s+\w+\s*\([^)]*\)\s*(throws\s+[\w.,\s]+)?\s*\{)')
comment_pattern = re.compile(r'\s*/\*\*')
missing = []
for path in sorted(root.rglob('*.java')):
    lines = path.read_text(encoding='utf-8').splitlines()
    for i, line in enumerate(lines):
        if method_pattern.match(line):
            j = i - 1
            while j >= 0 and lines[j].strip() == '':
                j -= 1
            if j < 0 or not comment_pattern.search(lines[j]):
                missing.append((path.relative_to(root), i + 1, line.strip()))
print('TOTAL', len(missing))
for p, l, ln in missing:
    print(p, l, ln)
