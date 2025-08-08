package com.arkhe.pinlock.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PinPadComponent(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Row 1: 1, 2, 3*/
        Row(
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            PinButton("1") { onNumberClick("1") }
            PinButton("2") { onNumberClick("2") }
            PinButton("3") { onNumberClick("3") }
        }

        /*Row 2: 4, 5, 6*/
        Row(
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            PinButton("4") { onNumberClick("4") }
            PinButton("5") { onNumberClick("5") }
            PinButton("6") { onNumberClick("6") }
        }

        /*Row 3: 7, 8, 9*/
        Row(
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            PinButton("7") { onNumberClick("7") }
            PinButton("8") { onNumberClick("8") }
            PinButton("9") { onNumberClick("9") }
        }

        /*Row 4: Clear, 0, Backspace*/
        Row(
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            PinIconButton(
                icon = Icons.Outlined.Refresh,
                contentDescription = "Clear PIN",
                onClick = onClearClick
            )
            PinButton("0") { onNumberClick("0") }
            PinIconButton(
                icon = Icons.Outlined.ArrowBackIosNew,
                contentDescription = "Backspace",
                onClick = onBackspaceClick
            )
        }
    }
}

@Composable
private fun PinIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .size(80.dp)
            .scale(if (isPressed) 0.95f else 1f)
            .background(
                color = if (isPressed)
                    MaterialTheme.colorScheme.secondary
                else
                    MaterialTheme.colorScheme.secondaryContainer,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isPressed)
                MaterialTheme.colorScheme.onSecondary
            else
                MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun PinButton(
    text: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .size(80.dp)
            .scale(if (isPressed) 0.95f else 1f)
            .background(
                color = if (isPressed)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPressed)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun PinIndicator(
    pinLength: Int,
    maxLength: Int = 6,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(maxLength) { index ->
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = if (index < pinLength) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}