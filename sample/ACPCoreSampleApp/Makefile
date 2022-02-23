sync:
	(cd ../../ && rm adobe-react-native-acpcore-2.1.0.tgz || true)
	(cd ../../ && npx run tsc)
	(cd ../../ && npm pack)
	(rm -r node_modules/@adobe/react-native-acpcore || true)
	(rm package-lock.json || true)
	(npm cache clean --force && npm install)